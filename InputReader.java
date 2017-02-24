import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public final class InputReader extends Application {

	private Desktop desktop = Desktop.getDesktop();

	@Override
	public void start(final Stage stage) {

		stage.setTitle("File Chooser Sample");

		final FileChooser fileChooser = new FileChooser();

		final Button openButton = new Button("Open File");
		final Button openMultipleButton = new Button("Load File");

		openButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				configureFileChooser(fileChooser);
				File file = fileChooser.showOpenDialog(stage);
				if (file != null) {
					openFile(file);
				}
			}
		});
		
		openMultipleButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				configureFileChooser(fileChooser);
				File file = fileChooser.showOpenDialog(stage);
				if (file != null) {
					BufferedReader fileReader = null;
					String line = "";
					String delimiter = " ";
					try {
//						ArrayList<Object> fileItems = new ArrayList<Object>();
						fileReader = new BufferedReader(new FileReader(file));
						line = fileReader.readLine();
						String[] header = line.split(delimiter);
						int num_videos = Integer.parseInt(header[0]);
						int num_endpoints = Integer.parseInt(header[1]);	
						int num_requestdesc = Integer.parseInt(header[2]);
						int num_cachecenters = Integer.parseInt(header[3]);
						int size_cachecenter = Integer.parseInt(header[4]);
						
						line = fileReader.readLine();
						String[] videosize = line.split(delimiter);						

						ArrayList<ArrayList<String[]>> endpointsinfo = new ArrayList<ArrayList<String[]>>();
						ArrayList<String[]> endpointscount = new ArrayList<String[]>();
						
											
						for (int u = 0; u<num_endpoints; u++) {
//						while ((line = fileReader.readLine()) != null) {
							line = fileReader.readLine();
							String[] endpoint = line.split(delimiter);
							int n = Integer.parseInt(endpoint[1]);
							ArrayList<String[]> cachecenterlatency = new ArrayList<String[]>();
							for (int i = 0; i < n; i++) {
								line = fileReader.readLine();
								String[] tmp = line.split(delimiter);
								cachecenterlatency.add(tmp);
							}
							endpointscount.add(endpoint);
							endpointsinfo.add(cachecenterlatency);
						}
						
						
//						ArrayList<ArrayList<String[]>> allrequests_per_endpoint = new ArrayList<ArrayList<String[]>>();
						ArrayList<String[]> videorequests_per_endpoint = new ArrayList<String[]>();
						for (int u = 0; u<num_requestdesc; u++) {
							line = fileReader.readLine();
							String[] videorequests = line.split(delimiter);	
							videorequests_per_endpoint.add(videorequests);
						}
						
						// Find max requests
						int max_requests = 0;
						for (int u = 0; u<videorequests_per_endpoint.size(); u++) {
							max_requests = Math.max(Integer.parseInt(videorequests_per_endpoint.get(u)[3]), max_requests);
						}

						while ((line = fileReader.readLine()) != null) {
							System.out.println("shouldnt be here");
//							Object[] tmp = line.split(delimiter);
//							for (Object item : tmp) {
//								fileItems.add(item);
//							}
						}
						
						System.out.println("Number of videos: " + num_videos);
						System.out.println("Number of endpoints: " + num_endpoints);
						System.out.println("Number of requests descriptions: " + num_requestdesc);
						System.out.println("Number of cache centers: " + num_cachecenters);
						System.out.println("Size of cache centers: " + size_cachecenter);
						
						System.out.print("Second line: " );
						for (String s : videosize) {
							System.out.println(s);
						}
						System.out.print("\n");
//						System.out.println(endpointsinfo);
						
						int[][] cacheServerVideosAssigned;
						
						for (int i=0; i < num_cachecenters; i++){
							
						}
					} catch (IOException io) {

					}
				}
			}
		});

		final GridPane inputGridPane = new GridPane();

		GridPane.setConstraints(openButton, 0, 0);
		GridPane.setConstraints(openMultipleButton, 1, 0);
		inputGridPane.setHgap(6);
		inputGridPane.setVgap(6);
		inputGridPane.getChildren().addAll(openButton, openMultipleButton);

		final Pane rootGroup = new VBox(12);
		rootGroup.getChildren().addAll(inputGridPane);
		rootGroup.setPadding(new Insets(12, 12, 12, 12));

		stage.setScene(new Scene(rootGroup));
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	private static void configureFileChooser(final FileChooser fileChooser) {
		fileChooser.setTitle("Select file");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
	}

	private void openFile(File file) {
		try {
			desktop.open(file);
		} catch (IOException ex) {
			Logger.getLogger(InputReader.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}