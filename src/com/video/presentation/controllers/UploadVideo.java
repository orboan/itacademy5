package com.video.presentation.controllers;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.awt.event.WindowEvent;

import com.video.domain.Usuari;
import com.video.domain.Video;
import com.video.presentation.views.MenuTui;
import com.video.presentation.views.UploadProgressBar;

public class UploadVideo implements Runnable {
	Thread t;

	//valors per defecte
	int msUploading = 3000;
	int msVerifying = 5000;

	MainController mainController;

	boolean uploaded;
	Video video;
	Usuari user;

	UploadVideo(MainController controller) {
		this.mainController = controller;
		t = new Thread(this, "UploadVideo");
	}

	@Override
	public void run() {
		if (video != null) {

			if (user != null)
				uploaded = addVideoToUser(video, user);
			else
				uploaded = makeVideoPublic(video);

		}
	}

	synchronized private boolean makeVideoPublic(Video video) {
		boolean added = false;
		MenuTui men = mainController.createMakeVideoPublicMenu(video);
		men.show();
		if (men.isQuit()) {
			mainController.exit = true; // exit
			return false;
		}
		
		if (men.getSelectedOption() == 1 || men.getSelectedOption() == -1) {
			VideoController vcontroller = VideoController.getInstance(men, mainController.ds);
			// Ja tenim el vídeo
			// Ara afegim el vídeo al repositori
			
			try {
				if (vcontroller != null) {
					JFrame frame;
					boolean alreadyUploaded = false;
					
					if (video.getUploadState() == null || !video.getUploadState().equals(Video.UploadState.UPLOADED))
						frame = uploadAndVerify(video);
					else {
						frame = new JFrame();
						alreadyUploaded = true;
					}
					added = vcontroller.save();// save video to repo
					if (added) {
						video.setUploadState(Video.UploadState.UPLOADED);
						if(alreadyUploaded) {
							frame.setVisible(true);
							setJFrameToUploaded(frame, ">>>> Already Uploaded! <<<<");
							}	
						else 
							setJFrameToUploaded(frame);	
						Thread.sleep(3000);
						frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
						
						System.out.println(mainController.messages.newVideoCreatedAndSavedToRepoInfo);// feedback
						// System.out.println(video.toString());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		return added;
	}

	synchronized private boolean addVideoToUser(Video video, Usuari user) {
		MenuTui men = mainController.createAssignVideoToUserMenu(video);
		men.show();
		if (men.isQuit()) {
			mainController.exit = true; // exit
			return false;
		}
		if (men.getSelectedOption() == 1 || men.getSelectedOption() == -1) {
			// Check if video already is added to the user
			UsuariController controller = UsuariController.getInstance(user, mainController.ds);
			if (controller.videoExistsInUserAccount(video)) {
				System.out.println(mainController.messages.videoAlreadyExistsInfo);// feedback
			} else {
				try {
					JFrame frame = uploadAndVerify(video);
					if (controller.addVideoToUser(video)) {// add video to user
						video.setUploadState(Video.UploadState.UPLOADED);
						setJFrameToUploaded(frame);						
						System.out.println(mainController.messages.videoAddedToUserInfo);// feedback
						Thread.sleep(3000);
						frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
						return true;
					}
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		return false;
	}

	void runProgressBar(int ms, JFrame frame, String title) throws InterruptedException {
		int step = 100;
		UploadProgressBar pb = new UploadProgressBar(ms);

		frame.setTitle(title);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setContentPane(pb);
		frame.setPreferredSize(new Dimension(500, 100));
	    frame.pack();
	    frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		for (int i = pb.MIN; i <= pb.MAX; i += step) {
			final int percent = i;

			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					pb.updateBar(percent);
				}
			});
			Thread.sleep(step);

		}

	}

	private JFrame uploadAndVerify(Video video) throws InterruptedException {
		video.setUploadState(Video.UploadState.UPLOADING);
		JFrame frame = new JFrame();
		runProgressBar(msUploading, frame, "Uploading");
		video.setUploadState(Video.UploadState.VERIFYING);
		runProgressBar(msVerifying, frame, "Verifying");
		return frame;
	}

	private void setJFrameToUploaded(JFrame frame) {
		setJFrameToUploaded(frame, "Video Uploaded!");
	}
	
	private void setJFrameToUploaded(JFrame frame, String title){
		JLabel label = new JLabel(title);
		label.setMinimumSize(new Dimension(500, 100));
		frame.getContentPane().removeAll();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setContentPane(label);
		frame.setPreferredSize(new Dimension(500, 100));
	    frame.pack();
	    frame.setLocationRelativeTo(null);
		frame.setTitle(title);
		frame.repaint();
	}

	public void setVideo(Video video) {
		this.msUploading = video.getDurada() * 1000 / 3;
		this.msVerifying = video.getDurada() * 1000 / 2;
		this.video = video;
	}

	public void setUser(Usuari user) {
		this.user = user;
	}

	public void setMsUploading(int msUploading) {
		this.msUploading = msUploading;
	}

	public void setMsVerifying(int msVerifying) {
		this.msVerifying = msVerifying;
	}

}
