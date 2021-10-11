package com.video.app;

import java.util.Scanner;

import com.video.persistence.repos.IDataSource;
import com.video.persistence.repos.VolatileRepo;
import com.video.presentation.controllers.MainController;

public class AppVideo {

	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		IDataSource ds = VolatileRepo.getInstance();
		//IDataSource ds = MySqlRepo.getInstance(); //Dummy
		MainController app = MainController.getInstance(in, ds);
		app.run();
		in.close();
	}
	
	
}
