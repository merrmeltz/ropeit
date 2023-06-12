import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class DataReader {
	
	private double[][] accelerationData;
	
	
	/**
	 * Returns a double array of accelerations corresponding to one shot.
	 * The index parameter is the shot number.
	 * @param index
	 * @return double[751][3] of accelerations (1st column x, 2nd column y, 3rd column z)
	 */
	public double[][] getData(int index){
		double[][] shotData = new double[751][3];
		for (int row = 0; row < 751; row++){
			for (int col = 3*(index-1); col < 3*(index); col++){
				shotData[row][col%3] = accelerationData[row][col];
			}
		}
		return shotData;
	}
	/**
	 * Creates a new DataReader. This automatically reads in accelerations from 
	 * a file with 30 test shots. (The file originally had 10; we added
	 * more to it). The data is stored in a double array. 
	 */
	public DataReader(){
		accelerationData = new double[751][90];
		try {
			BufferedReader bReader = new BufferedReader(new FileReader("src/10ShotsTestingData.csv"));
			try {
				String dataRow = bReader.readLine();
				dataRow = bReader.readLine(); // skip the first line with the titles
				int row = 0;
				while (dataRow != null){
					String[] dataArray = dataRow.split(",");
					for (int col = 0; col < dataArray.length; col++){
						accelerationData[row][col] = Double.valueOf(dataArray[col]);
					}
					row++;
					dataRow = bReader.readLine();
				}
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
	
	

}
