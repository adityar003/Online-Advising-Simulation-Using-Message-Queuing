package ds;

//Reference: https://www.youtube.com/watch?v=GURClZeR96E&t=662s
//Aditya Ravikumar
//1001672163

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Notification {

	public static void main(String[] args) 
	{
		try
		{	
			//Returns a reference to the remote object Registry on the specified host and port.
			Registry reg= LocateRegistry.getRegistry("127.0.0.1",1094);
			MessageInterface mi=(MessageInterface)reg.lookup("vkbind");
			int z=0,count=0;
			//a jframe is used inside which the textarea is used
			JFrame jframe = new JFrame();
			JPanel jp=new JPanel();
			//the message is displayed in this textarea
			JTextArea jta = new JTextArea(60,50);
			jframe.setSize(600, 600);
			//this is used to set the jframe in the middle
			jframe.setLocationRelativeTo(null);
			//i am setting the title of the jframe to advisor 
			jframe.setTitle("NOTIFICATION");
			//this is used to add the scrollbar to textarea
			JScrollPane scroll = new JScrollPane(jta,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scroll.setViewportView(jta);
		    //i am adding the scrollbar to jframe
			jframe.add(scroll);
			//the textarea should not be allowed to be edited
			jta.setEditable(false);
			jp.add(jta);
			jframe.add(jp);
			//we are setting the jframe to visible
			jframe.setVisible(true);
			
		    System.out.println("------------NOTIFICATION PROCESS STARTED---------");
			while(z==0)
			{
				//we get the advisor response from the queue
				ConcurrentHashMap<String, String>  h=mi.getAdvisorResponse();
				//we check whether there are any responses
				if(h.size()!=0)
				{		
						//for each course we display the decision 
						for ( String key : h.keySet() ) {
							// when this class gets executed for the first time then count will be 0 and we can create a new textarea else we will append the message to the existing textarea
							if(count==0)
							{
								jta.setText("COURSE NAME: '"+key+"' ADVISOR DECISION: '"+h.get(key)+"' \n");
								System.out.println("");
								System.out.println("COURSE NAME: 	  "+key);
								System.out.println("ADVISOR DECISION: "+h.get(key));
								count++;
							}
							else
							{
								jta.append("COURSE NAME: '"+key+"' ADVISOR DECISION: '"+h.get(key)+"' \n");
								System.out.println("");
								System.out.println("COURSE NAME: 	  "+key);
								System.out.println("ADVISOR DECISION: "+h.get(key));
							}
						}
				}	
				else
				{
					long start = System.currentTimeMillis();
					long end = start + 7*1000; // 7 seconds 
					//the control stays inside the loop for 7 seconds
					while (System.currentTimeMillis() < end)
					{}
					//if there are no responses the control goes in to this loop 
					if(count==0)
					{
							count++; 
							jta.setText("WAITING FOR THE RESPONSE FROM ADVISOR PROCESS. \n");
							System.out.println("");
							System.out.print("WAITING FOR THE RESPONSE FROM ADVISOR PROCESS.");
					}
					else
					{
						jta.append("WAITING FOR THE RESPONSE FROM ADVISOR PROCESS. \n");
						System.out.println("WAITING FOR THE RESPONSE FROM ADVISOR PROCESS.");
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
