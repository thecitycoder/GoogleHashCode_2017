
public class CacheServer {
	
	int serverNumber;
	int maxCapacity;
	VideoPool storedVideos;
	VideoPool usableVideos;
	
	
	public CacheServer(int serverNumber , int maxCapacity) {

		this.serverNumber = serverNumber;
		this.maxCapacity = maxCapacity;
	
	}
	
}
