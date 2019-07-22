//reference : https://www.youtube.com/watch?v=GURClZeR96E&t=662s
// https://docs.oracle.com/javase/tutorial/rmi/implementing.html
//Aditya Ravikumar
//1001672163
package ds; 

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Student {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		try{
			//Returns a reference to the remote object Registry on the specified host and port.
			Registry reg= LocateRegistry.getRegistry("127.0.0.1",1094);
			MessageInterface mi=(MessageInterface)reg.lookup("vkbind");
			int i=0;
			//we take the name from the user
			System.out.println("Enter your name: ");
			Scanner scanner = new Scanner(System.in);
			String name = scanner.nextLine();
			while(i==0)
			{
				//we take the course name from user
				System.out.println("Enter the course name to be waived: ");
				scanner = new Scanner(System.in);
				String course=scanner.nextLine();
				//we invoke the method to send the name and course
				mi.studentRequest(name, course);
				//this checks whether the user wants to enter the course again 
				System.err.println("Do you want to enter other courses(y/n): ");
				while(true){
					scanner = new Scanner(System.in);
					String choice=scanner.nextLine();
					//if he is not interested then it will end the student process 
					if(choice.toLowerCase().equals("n"))
					{
						System.out.print("-------END OF STUDENT PROCESS-----");
						i=1;
						break;
					}
					else if(choice.toLowerCase().equals("y")) 
					{
						break;
					}
					else
						System.err.println("Please enter valid option: Enter 'Y' to waive another course and 'N' to stop");
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();	
		}	
	}
}

