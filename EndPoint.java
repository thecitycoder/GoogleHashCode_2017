import java.util.HashMap;

public class EndPoint {
	
	int endPointNumber;
	int dataCenterLatency;
	int numberOfConnectedCacheServers;
	RequestDescription[] requestDescriptions;
//	Stores a list of CacheServers as key and 
//	the corresponding value is the latency of its connection to the cache server
	HashMap<CacheServer, Integer> connectedCacheServers;
	
	public EndPoint(int endPointNumber, int dataCenterLatency, int numberOfConnectedCacheServers ) {

		this.endPointNumber = endPointNumber;
		this.dataCenterLatency = dataCenterLatency;
		this.numberOfConnectedCacheServers = numberOfConnectedCacheServers;
	
	}
	
	void addConnectedCacheServer(CacheServer cacheServer, Integer cacheServerLatency){
		this.connectedCacheServers.put(cacheServer, cacheServerLatency);
	}

}
