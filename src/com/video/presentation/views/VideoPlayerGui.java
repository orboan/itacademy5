package com.video.presentation.views;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.video.domain.PlayerState;
import com.video.domain.Video;

public class VideoPlayerGui {
    private Video video;    
    
    private boolean stopped = true, playing, paused, finished;

    public VideoPlayerGui(Video video) {
        this.video = video;
    }

    public void init() {
        JFrame frame = new JFrame("Video player");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.out.println("\nVideo Player closed by user.");
                finished = true;
                e.getWindow().dispose();
            }
        });
        JPanel context = new JPanel();
        context.setLayout(new BoxLayout(context, BoxLayout.Y_AXIS));
        frame.getContentPane().add(context);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JTextField textField = new JTextField();
        JTextField textField2 = new JTextField();
        textField.setText(PlayerState.VideoState.STOPPED.name());
        textField2.setText("Time elapsed:\t0s");
        context.add(textField);
        context.add(textField2);
        UploadProgressBar pb = new UploadProgressBar(video.getDurada() * 1000);
        context.add(pb);
        context.add(buttons);

        // Context delegates handling user's input to a state object. Naturally,
        // the outcome will depend on what state is currently active, since all
        // states can handle the input differently.
        JButton stop = new JButton("Stop");
        stop.addActionListener(e -> { 
        	textField.setText(video.getPlayerState().onStop().name());
        	stopped = true; playing = false; paused = false;
        });
        JButton play = new JButton("Play");
        play.addActionListener(e -> {
        	textField.setText(video.getPlayerState().onPlay().name());
        	stopped = false; playing = true; paused = false;   
        });
        JButton pause = new JButton("Pause");
        pause.addActionListener(e -> {
        	textField.setText(video.getPlayerState().onPause().name());
        	stopped = false; playing = false; paused = true;
        });        
        buttons.add(stop);
        buttons.add(play);
        buttons.add(pause);
		frame.setPreferredSize(new Dimension(500, 200));
	    frame.pack();
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	    try {
			runProgressBar(pb, textField2);
			if(finished)
				textField.setText("Finished !!");
			else {
				System.out.println("\nInfo: player has been closed before it finished playing");
			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

    }
    
	void runProgressBar(UploadProgressBar pb, JTextField textField) throws InterruptedException {
		int step = 1000;

		for (int i = pb.MIN; i <= pb.MAX && !finished; i += step) {
			if(stopped) i = pb.MIN;
			else if(paused) i -= step;
			final int percent = i;

			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					pb.updateBar(percent);
					textField.setText("Time elapsed:\t" + (percent / 1000) + "s");
				}
			});
			Thread.sleep(step);
		}
		this.finished = true;

	}
}
