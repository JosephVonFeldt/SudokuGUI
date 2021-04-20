import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.SpringLayout;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JTextField;

public class Sudoku {

	private JFrame frmSudoku;
	private JTextField[][][][] boxes = new JTextField[3][3][3][3];
	private int[][][][] clues = {{{{9, 6, 0}, {0, 0, 0}, {4, 0, 0}}, {{3, 0, 0}, {0, 2, 0}, {0, 0, 0}}, {{8, 0, 1}, {3, 0, 0}, {0, 0, 0}}}, {{{5, 0, 0}, {0, 0, 9}, {0, 0, 0}}, {{0, 0, 0}, {0, 0, 4}, {6, 8, 0}}, {{0, 2, 7}, {0, 0, 0}, {0, 0, 0}}}, {{{0, 0, 0}, {0, 5, 0}, {0, 0, 0}}, {{1, 6, 2}, {4, 0, 0}, {0, 0, 0}}, {{0, 9, 8}, {1, 0, 0}, {0, 0, 0}}}};
		//{{{{5, 3, 0}, {6, 0, 0}, {0, 9, 8}}, {{0, 7, 0}, {1, 9, 5}, {0, 0, 0}}, {{0, 0, 0}, {0, 0, 0}, {0, 6, 0}}}, {{{8, 0, 0}, {4, 0, 0}, {7, 0, 0}}, {{0, 6, 0}, {8, 0, 3}, {0, 2, 0}}, {{0, 0, 3}, {0, 0, 1}, {0, 0, 6}}}, {{{0, 6, 0}, {0, 0, 0}, {0, 0, 0}}, {{0, 0, 0}, {4, 1, 9}, {0, 8, 0}}, {{2, 8, 0}, {0, 0, 5}, {0, 7, 9}}}};
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sudoku window = new Sudoku();
					window.frmSudoku.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Sudoku() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmSudoku = new JFrame();
		frmSudoku.setResizable(false);
		frmSudoku.setTitle("Sudoku");
		frmSudoku.setBounds(100, 100, 660, 420);
		frmSudoku.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmSudoku.getContentPane().setLayout(springLayout);
		
		JButton checkButton = new JButton("Check");
		springLayout.putConstraint(SpringLayout.WEST, checkButton, 126, SpringLayout.WEST, frmSudoku.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, checkButton, -5, SpringLayout.SOUTH, frmSudoku.getContentPane());
		frmSudoku.getContentPane().add(checkButton);
		
		JButton solveButton = new JButton("Solve");
		springLayout.putConstraint(SpringLayout.WEST, solveButton, 40, SpringLayout.EAST, checkButton);
		springLayout.putConstraint(SpringLayout.SOUTH, solveButton, 0, SpringLayout.SOUTH, checkButton);
		solveButton.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	System.out.println(Arrays.deepToString(clues));
			    try {
			    	int[][][][] solution = solvePuzzle(clues);
			    	showSolution(solution);
			    }catch (Exception err) {
			    	System.out.println(err);
			    }
		    	
		    }
		});
		frmSudoku.getContentPane().add(solveButton);
		
		
		JPanel outer = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, outer, -332, SpringLayout.NORTH, checkButton);
		springLayout.putConstraint(SpringLayout.WEST, outer, 77, SpringLayout.WEST, frmSudoku.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, outer, -6, SpringLayout.NORTH, checkButton);
		springLayout.putConstraint(SpringLayout.EAST, outer, 538, SpringLayout.WEST, frmSudoku.getContentPane());
		frmSudoku.getContentPane().add(outer);
		outer.setLayout(new GridLayout(3, 3, 0, 0));
		outer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		
		
		JButton setClues = new JButton("Set Clues");
		springLayout.putConstraint(SpringLayout.NORTH, setClues, 41, SpringLayout.NORTH, frmSudoku.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, setClues, 6, SpringLayout.EAST, outer);
		setClues.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	int[][][][] newClues = new int[3][3][3][3];
				for(int i=0; i<3; i++) {
					for(int j=0; j<3; j++) {
						for(int x=0; x<3; x++) {
							for(int y=0; y<3; y++) {
								try {
								newClues[i][j][x][y] = Integer.parseInt(boxes[i][j][x][y].getText());
								}catch(Exception ex){
									newClues[i][j][x][y] = 0;
								}
								
							}
						}
					}
				}
				updateClues(newClues);
		    }
		});
		frmSudoku.getContentPane().add(setClues);
		Font font1 = new Font("SansSerif", Font.BOLD, 20);
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				JPanel inner= new JPanel(new GridLayout(3, 3));
				inner.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				for(int x=0; x<3; x++) {
					for(int y=0; y<3; y++) {
						boxes[i][j][x][y] = new JTextField();
						boxes[i][j][x][y].setBorder(BorderFactory.createLineBorder(Color.GRAY));
						boxes[i][j][x][y].setHorizontalAlignment(JTextField.CENTER);
						JTextField box = boxes[i][j][x][y];
						box.setFont(font1);
						box.addKeyListener(new KeyAdapter() {
						    public void keyTyped(KeyEvent e) { 
						        if (box.getText().length() >= 1 ) {
						            e.consume(); 
						        }
						    }  
						});
						inner.add(boxes[i][j][x][y],BorderLayout.CENTER);
					}
				}
				outer.add(inner);
			}
		}
		updateClues(clues);
	}
	public void updateClues(int[][][][] newClues) {
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				for(int x=0; x<3; x++) {
					for(int y=0; y<3; y++) {
						JTextField box = boxes[i][j][x][y];
						clues[i][j][x][y] = newClues[i][j][x][y];
						if (newClues[i][j][x][y] == 0) {
							box.setEditable(true);
							box.setText("");
						}else {
							box.setEditable(false);
							box.setText(String.valueOf(clues[i][j][x][y]));
						}
						
					}
				}
			}
		}
	}
	public int[][][][] solvePuzzle(int[][][][] puzzle) throws Exception{
		int [] empty = nextEmpty(puzzle);
		int[][][][] solution = null;
		if (empty != null) {
			int i = empty[0];
			int j = empty[1];
			int x = empty[2];
			int y = empty[3];
			boolean[] moves = legalMoves(empty, puzzle);
			int count = 0;
			for(int k = 1; k<10; k++) {
				if(moves[k]) {
					count++;
					int[][][][] temp = deepCopyPuzzle(puzzle);
					temp[i][j][x][y] = k;
					//System.out.println(Arrays.deepToString(temp));
					int[][][][] tempSolution = solvePuzzle(temp);
					if (tempSolution != null) {
						if (solution!=null) {
							throw new Exception("Multiple Solutions");
						}
						int [] empty2 = nextEmpty(tempSolution);
						if (empty2 == null) {
							solution = tempSolution;
						}
					}
					
					
				}
			}
			if (count == 0) {
				return null;
			}
			
		}else {
			return puzzle;
		}
		return solution;
	}
	public void showSolution(int[][][][] solution) {
		System.out.println(Arrays.deepToString(solution));
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				for(int x=0; x<3; x++) {
					for(int y=0; y<3; y++) {
						JTextField box = boxes[i][j][x][y];
						try {
							box.setText(String.valueOf(solution[i][j][x][y]));
						} catch (Exception ere) {
						}
					}
				}
			}
		}
	}
	public int[] nextEmpty(int[][][][] puzzle){
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				for(int x=0; x<3; x++) {
					for(int y=0; y<3; y++) {
						if (puzzle[i][j][x][y] == 0 ) {
							return new int[] {i,j,x,y};
						}
					}
				}
			}
		}
		return null;
	}
	public boolean[] legalMoves(int[] location, int[][][][] puzzle){
		
		boolean[] legalMoves = {false,true,true,true,true,true,true,true,true,true};
		
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				for(int x=0; x<3; x++) {
					for(int y=0; y<3; y++) {
						if (inRowColumnSquare(i, j, x, y, location)) {
							legalMoves[puzzle[i][j][x][y]] = false;
						}
					}
				}
			}
		}
		return legalMoves;
	}
	
	public boolean inSquare(int i, int j, int[] location){
		return (i == location[0]) && (j == location[1]);
	}
	public boolean inRow(int i, int x, int[] location){
		return (i == location[0]) && (x == location[2]);
	}
	public boolean inColumn(int j, int y, int[] location){
		return (j == location[1]) && (y == location[3]);
	}
	public boolean inRowColumnSquare(int i, int j, int x, int y, int[] location){
		return inSquare(i, j, location) || inColumn(j, y, location) || inRow(i, x, location);
	}
	public int[][][][] deepCopyPuzzle(int[][][][] puzzle){
		int[][][][] copy = new int[3][3][3][3];
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				for(int x=0; x<3; x++) {
					for(int y=0; y<3; y++) {
						copy[i][j][x][y] = puzzle[i][j][x][y];
					}
				}
			}
		}
		return copy;
	}
}
