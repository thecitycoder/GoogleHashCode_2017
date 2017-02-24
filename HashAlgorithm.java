import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public final class HashAlgorithm {

	String path;
	
	int NUMBEROFVIDEOS;
	int NUMBEROFENDPOINTS;
	int NUMBEROFREQUESTDESCRIPTIONS;
	int NUMBEROFCACHECENTERS;
	int CACHECENTERCAPACITY;
	
	Video[] allVideos;
	EndPoint[] endPoints;
	CacheServer[] cacheServers;
	RequestDescription[] requestDescriptions;
	int[] videoSizes;
	
	ArrayList<Video> videosBelowCacheCapacity = new ArrayList<>();
 	
	public HashAlgorithm( String filePath) {
		this.path = filePath;
		
		BufferedReader textReader = openFile(path);
		fileProcessing(textReader);
		
		this.videosBelowCacheCapacity = filterVideosBelowCacheCapacity(allVideos);
		
	}
	
	@SuppressWarnings("null")
	private ArrayList<Video> filterVideosBelowCacheCapacity(Video[] videos) {
		
		ArrayList<Video> filteredList = null;
		
		for (int i =0 ; i < videos.length ; i++){
			if (videos[i].videoSize<CACHECENTERCAPACITY){
				filteredList.add(videos[i]);
			}
		}
		
		return filteredList;
	}

	BufferedReader openFile (String path){
	
		BufferedReader textReader = null;
		
		try {
			FileReader fileReader = new FileReader(path);
			textReader = new BufferedReader(fileReader);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return textReader;
	}

	void fileProcessing (BufferedReader textReader){
		
//		int lineNumber = 0;
		String line = "";
		String delimiter = " ";
		
		String[] firstLine;
		String[] videoSizes;
		int[] lineArray;	
		
		try {
			line = textReader.readLine();
			firstLine = line.split(delimiter);
			
			NUMBEROFVIDEOS = Integer.parseInt(firstLine[0]);
			NUMBEROFENDPOINTS = Integer.parseInt(firstLine[1]);
			NUMBEROFREQUESTDESCRIPTIONS = Integer.parseInt(firstLine[2]);
			NUMBEROFCACHECENTERS = Integer.parseInt(firstLine[3]);
			CACHECENTERCAPACITY = Integer.parseInt(firstLine[4]);
			
			allVideos = new Video[NUMBEROFVIDEOS];
			endPoints = new EndPoint[NUMBEROFENDPOINTS];
			cacheServers = new CacheServer[NUMBEROFCACHECENTERS];
			requestDescriptions = new RequestDescription[NUMBEROFREQUESTDESCRIPTIONS];
			
			line = textReader.readLine();
			videoSizes = line.split(delimiter);
			
			this.videoSizes = stringArrayToIntegerArray(videoSizes);
			
			for (int i =0 ; i < NUMBEROFVIDEOS; i++){
				allVideos[i]=new Video(i, this.videoSizes[i]);
			}
			
			for (int i =0 ; i < NUMBEROFCACHECENTERS; i++){
				cacheServers[i]= new CacheServer(i, CACHECENTERCAPACITY );
			}
			
			for (int i = 0 ; i < NUMBEROFENDPOINTS; i++){
				line = textReader.readLine();
				lineArray = stringArrayToIntegerArray(line.split(delimiter));
				
				endPoints[i] = new EndPoint(i, lineArray[0], lineArray[1]);
				int numberOfConnectedCacheServers = lineArray[1];
			
				for (int j = 0; j< numberOfConnectedCacheServers; j++ ){
					line = textReader.readLine();
					lineArray = stringArrayToIntegerArray(line.split(delimiter));
					if (endPoints[i].dataCenterLatency > lineArray[1]){
						endPoints[i].addConnectedCacheServer(cacheServers[lineArray[0]], lineArray[1]);
					}
				}
			}
			
			for (int i = 0 ; i < NUMBEROFREQUESTDESCRIPTIONS; i++){
				line = textReader.readLine();
				lineArray = stringArrayToIntegerArray(line.split(delimiter));
			
				requestDescriptions[i] = new RequestDescription(lineArray[1]
						, allVideos[lineArray[0]] , lineArray[2]);
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private int[] stringArrayToIntegerArray(String[] stringArray) {
		int size = stringArray.length;
		int[] integerArray = new int[size];
		
		for (int i = 0; i < size ; i++){
			integerArray[i] = Integer.parseInt(stringArray[i]);
		}
		return integerArray;
	}

}