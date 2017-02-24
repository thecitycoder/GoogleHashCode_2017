
public class RequestDescription {
	
	int reqestingEndpoint;
//	int requestDescriptionNumber;
	Video requestedVideo;
	int numberOfRequests;
	
	public RequestDescription(int reqestingEndpoint, Video requestedVideo, int numberOfRequests ) {

		this.reqestingEndpoint = reqestingEndpoint;
		this.requestedVideo = requestedVideo;
		this.numberOfRequests = numberOfRequests;
	
	}
	
	

}
