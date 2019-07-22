package ds;

/*Reference:  https://docs.oracle.com/javase/tutorial/rmi/implementing.html
https://www.youtube.com/watch?v=GURClZeR96E&t=662s*/

//package DSProj3; 
//Aditya Ravikumar
//1001672163
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TimeZone;
import javax.swing.*;

public class Advisor{

	public static void main(String[] args) {
		try{
			//Returns a reference to the remote object Registry on the specified host and port.
			Registry reg= LocateRegistry.getRegistry("127.0.0.1",1094);
			MessageInterface im=(MessageInterface)reg.lookup("vkbind");
			//this variable stores the course list
			Queue<String> q=new LinkedList<String>();
			//this variabel is used to either open a new alert box or to append the message to existing box
			int count=0;
			//a jframe is used inside which the textarea is used
			JFrame jframe = new JFrame();
			JPanel jp=new JPanel();
			//the message is displayed in this textarea
			JTextArea jta = new JTextArea(60,50);
			jframe.setSize(600, 600);
			//this is used to set the jframe in the middle
			jframe.setLocationRelativeTo(null);
			//i am setting the title of the jframe to advisor 
			jframe.setTitle("ADVISOR");
			//this is used to add the scrollbar to textarea
			JScrollPane scroll = new JScrollPane(jta,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		    //i am adding the scrollbar to jframe
			jframe.add(scroll);
			//the textarea should not be allowed to be edited
			jta.setEditable(false);
			jp.add(jta);
			jframe.add(jp);
			//we are setting the jframe to visible
			jframe.setVisible(true);
			
			System.out.println("---------ADVISOR PROCESS STARTED--------");
			while(true){
				//we get the queue of course list
				q=im.advisorRequest();
				//if queue is empty then the control goes inside a loop for 3 seconds and checks again for the queue
				if(q.isEmpty())
				{
					long start = System.currentTimeMillis();
					long end = start + 3*1000; 
					//the control stays inside the loop for 3 seconds
					while (System.currentTimeMillis() < end)
					{}
					//here we either add the message to existing textarea or create a new textarea 
					if(count==1)
					{
						jta.setText("Waiting for response \n");
					}
					else
					{
						jta.append("Waiting for response \n");
					}
				}
				else
				{
					//for each value in the queue the decision is made
					for(String str:q)
					{
						//we take the time and convert it into milliseconds and add it to the random generated number and check whether it is odd or even
						Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
						long time = cal.getTimeInMillis();
						long random = (long )(Math.random() * 50000 + 1);
						time+=random;
						//if the number is even then the decision is no
						if(time % 2==0)
						{
							//here we either add the message to existing textarea or create a new textarea
							count++;
							if(count==1)
							{
								jta.setText("advisors decision for course '"+str+"' is 'NO' \n");
								count++;
							}
							else
							{
								jta.append("advisors decision for course '"+str+"' is 'NO' \n");
							}
							System.out.println("advisors decision for course "+str+" is 'NO'");
							//for each decision made the response is sent to the queue
							im.advisorResponse(str, "no");
						}
						else//if the number is odd then it is 'yes'
						{
							//here we either add the message to existing textarea or create a new textarea
							count++;
							if(count==1)
							{
								jta.setText("advisors decision for course '"+str+"' is 'YES' \n");
								count++;
							}
							else
							{
								jta.append("advisors decision for course '"+str+"' is 'YES' \n");
							}
							System.out.println("advisors decision for course "+str+" is 'YES'");	
							//for each decision made the response is sent to the queue
							im.advisorResponse(str, "yes");
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();	
		}	
	}
}
