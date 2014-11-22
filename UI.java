import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class UI extends JFrame{
	private JButton jbtSearch = new JButton("Search or add");
	private JTextField jtfInput = new JTextField(20);
	private JTextField jtfadd = new JTextField(10);
	//private String[] flagTitles = new String[20];//imagine 20 words
	Vector<String> vt = new Vector<String>();//try to use Vector instead of String
	private JList<String> jlst = new JList<String>();
	//private MessagePanel messagePanel = new MessagePanel();
	private MessageTextArea messageTextArea = new MessageTextArea();
	private String currentWord ;//save current value according to keyboard input
	//private boolean isSearching;//save the value of search button
	private Dictionary myDictionary = new Dictionary();//composition
	int index = 0;//the index of the first word of the imagination list
	
	private String addE;
	
	public UI(){
		//Use a panel to group the first line
		JPanel jpTextField = new JPanel();
		//jpTextField.setLayout(new BorderLayout(5,5));
		jpTextField.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
		jpTextField.add(new JLabel("Input your word or phrase:"),BorderLayout.WEST);
		jpTextField.add(jtfInput,BorderLayout.CENTER);
		jpTextField.add(jbtSearch,BorderLayout.EAST);
		jpTextField.add(new JLabel("Add:"));
		jpTextField.add(jtfadd);
		jbtSearch.setToolTipText("Search in the dictionary");
		jbtSearch.setMnemonic('S');
		
		//Set the width of JList
		jlst.setFixedCellWidth(200); 
		
		//Group the whole interface
		setLayout(new BorderLayout(5,5));
		add(jpTextField,BorderLayout.NORTH);
		add(new JScrollPane(jlst),BorderLayout.WEST);
		//add(messagePanel,BorderLayout.CENTER);
		add(new JScrollPane(messageTextArea),BorderLayout.CENTER);
		//add(messageTextArea,BorderLayout.CENTER);
		//set keyboard listener
		jtfInput.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e){// If the key is a character or space,change the currentWord,else ignore it.
				//test
				//System.out.println(e.getKeyCode());
				//System.out.println(currentWord);
				//System.out.println(e.getKeyChar()+" "+(int)e.getKeyChar());
				if((e.getKeyChar() >= 97 && e.getKeyChar() <= 122) || e.getKeyChar() == ' '
						||(e.getKeyChar() >= 65 && e.getKeyChar() <= 90)||e.getKeyCode()== e.VK_ENTER
						||e.getKeyChar()==8||e.getKeyChar()=='.'){
					
					currentWord = jtfInput.getText();
					currentWord = currentWord.trim();
					//System.out.println(currentWord);
					index = myDictionary.searchMethod2(currentWord);
					//if(index == -1)
						//System.out.println(index);
					if(index != -1){
						vt.clear();
						for(int i = 0;i < 50;i++){
							if(index+i < myDictionary.getNumberOfElements())
								vt.add(myDictionary.getWords(index+i));
							else break;
						}
						jlst.setListData(vt);
						if(e.getKeyCode()== e.VK_ENTER){
							//System.out.println("- -");
							messageTextArea.setMessage(myDictionary.getWords(index));
							String temp = "\n"+"英:"+"\n"+"\n"+"解释:"+"\n"+"    "+myDictionary.getExplains(index);
							messageTextArea.setText(temp);
							//System.out.println(temp);
						}
						//System.out.println(flagTitles[0]);
					}
					else {
						vt.clear();
						
						int startWithIndex =myDictionary.searchMethod(currentWord);//startWith search
						if(startWithIndex != -1)
						for(int i = 0;i < 50;i++){
							if(startWithIndex+i < myDictionary.getNumberOfElements())
								vt.add(myDictionary.getWords(startWithIndex+i));
							else break;
						}
						jlst.setListData(vt);
						if(e.getKeyCode()== e.VK_ENTER){//spelling error
							/*
							 * 
							 * extra
							 */
							
							myDictionary.subSearch(currentWord);
							
							if(myDictionary.longStr.compareTo("")!=0){
								//System.out.println("A");
								messageTextArea.setMessage(currentWord);
								messageTextArea.setText("\n"+"\n"+"\n"+"      You may be looking for:"+"\n"+"    "+myDictionary.longStr);
								myDictionary.longStr ="";
							}
							
							/*
							 * 
							 * 
							 */
							else{
						
							myDictionary.simClear();
							myDictionary.countSim(currentWord);
							messageTextArea.setMessage("NOT FOUND");
							String temp ="\n"+"\n"+"\n"+"      You may be looking for:"+"\n"+"    "+myDictionary.simDisplay();
							messageTextArea.setText(temp);
							//System.out.println(myDictionary.simDisplay());
						}
						}
					}
					
				}
				
			}
		});
		
		jtfadd.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e){
				/*if((e.getKeyChar() >= 97 && e.getKeyChar() <= 122) || e.getKeyChar() == ' '
						||(e.getKeyChar() >= 65 && e.getKeyChar() <= 90)||e.getKeyCode()== e.VK_ENTER
						||e.getKeyChar()==8||e.getKeyChar()=='.'){*/
			addE =jtfadd.getText(); 
				}
			}
			);
			
		//set button listener
		jbtSearch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(index != -1){
				//isSearching = true;
				//messagePanel.setMessage(myDictionary.getExplains(index));
				messageTextArea.setMessage(myDictionary.getWords(index));
				String temp = "\n"+"英:"+"\n"+"\n"+"解释:"+"\n"+"    "+myDictionary.getExplains(index);
				//System.out.println(temp);
				/*
				if(myDictionary.getExplains(index).length()>=250){
					System.out.println("ATTENTION");
					
					temp = myDictionary.getExplains(index).substring(0, 10)+"\n"+myDictionary.getExplains(index).substring(10);
					System.out.println(temp);
					messageTextArea.setText(temp);
					}
				else
				*/
				messageTextArea.setText(temp);
				}
				else{
					//add new words
					
					try {
						myDictionary.addNEW2(currentWord, addE);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//spelling error
					/*
					myDictionary.simClear();
					myDictionary.countSim(currentWord);
					messageTextArea.setMessage("NOT FOUND");
					String temp = "\n"+"\n"+"\n"+"      You may be looking for:"+"\n"+"    "+myDictionary.simDisplay();
					messageTextArea.setText(temp);
					*/
					//System.out.println(myDictionary.simDisplay());
				}
				//messageTextArea.setLineWrap(true);
				//messageTextArea.setWrapStyleWord(true);
			}
			
		});
		
		jlst.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if(index != -1 && jlst.getSelectedIndex()!=-1){
				int subIndex = jlst.getSelectedIndex() + index;
				//if(subIndex == -1)
					//System.out.println(index+"sub:"+subIndex);
				messageTextArea.setMessage(myDictionary.getWords(subIndex ));
				String temp = "\n"+"英:"+"\n"+"\n"+"解释:"+"\n"+"    "+myDictionary.getExplains(subIndex);
				messageTextArea.setText(temp);
				}
			}
			
		});
			
	}
	//get or change the value of private variables 
	
	
	public String getCurrentWord(){
		return currentWord;
	}
	//Change the list
	/*
	public void addStringToList(String string){
		for(int i = 0;i < 20;i ++){
			if(flagTitles[i] == null){
				flagTitles[i] = string;
			}
		}
	}
	*/
	public static void main(String[] args){
		JFrame frame = new UI();
		frame.pack();
		frame.setTitle("My dictionary");
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
}
