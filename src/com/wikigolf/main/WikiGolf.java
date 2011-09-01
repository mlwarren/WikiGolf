
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

public class WikiGolf {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		WikiGolf wg = new WikiGolf();
		String url = "http://en.wikipedia.org/wiki/Domestication";
		wg.directoryStructureProcess(url,2);
	}
	
	public void directoryStructureProcess(String parentUrls, int iterations){
		WikiSiteGet wsg = new WikiSiteGet();
	
		for(int i = 0; i<iterations; i++){
			Scanner parentScan = new Scanner(parentUrls);
			String oldDir="";
			String newParents = "";
			String title="";
			while(parentScan.hasNext()){
				try{
					String parentUrl = parentScan.next();
					title = wsg.getWebsiteTitle(parentUrl);
					String children = wsg.getRefinedWikiLinks(wsg.getWebpageInfo(parentUrl));
					Scanner childScan = new Scanner(children);
					boolean parentDir = (new File(oldDir+title)).mkdir();
					while(childScan.hasNext()){
						System.err.println("hey");
						String childUrl = "http://en.wikipedia.org"+childScan.next();
						String childTitle = wsg.getWebsiteTitle(childUrl);
						String subChildren = wsg.getRefinedWikiLinks(wsg.getWebpageInfo(childUrl));
						FileWriter fw = new FileWriter(title+"\\"+childTitle+".txt");
						fw.write("Parent URL is: " + parentUrl + "\n");
						fw.write("Current URL is: " + childUrl + "\n");
						fw.write(subChildren);
						fw.close();
						newParents += childUrl + "\n";
					}
				}
				catch(Exception e){
					System.err.println("Error! " + e);
					System.exit(1);
				}
			}
			oldDir += title+"\\";
			parentUrls=newParents;
		}
	}
	
	public void leveledDirectoryStructureProcess(String parentUrls, int iterations){
		WikiSiteGet wsg = new WikiSiteGet();
		String newParents = "";
		String dirPath = "";
		for(int i = 0; i<iterations; i++){
			dirPath += "level"+i+"\\";
			boolean make_my_dir = (new File(dirPath)).mkdir();
			if(!make_my_dir){
				System.err.println("Directory not made. Shutting down.");
				System.exit(1);
			}
			Scanner parentScan = new Scanner(parentUrls);
			while(parentScan.hasNext()){
				String parentUrl = parentScan.next();
				try{
					String title = wsg.getWebsiteTitle(parentUrl);
					FileWriter fw = new FileWriter(dirPath+"\\"+title+".txt");
					String children = wsg.getRefinedWikiLinks(wsg.getWebpageInfo(parentUrl));
					fw.write("Parent URL is: " + parentUrl + "\n\n");
					fw.write(children);
					fw.close();
					Scanner childScan = new Scanner(children);
					newParents = "";
					while(childScan.hasNext()){
						String childUrl = "http://en.wikipedia.org"+childScan.next();
						newParents += childUrl + "\n";
					}
				}
				catch(Exception e){
					System.err.println("Exception! " + e);
					System.exit(1);
				}
			}
			parentUrls=newParents;
		}
	
	}

	public void singleFileProcess(String parentUrls, int iterations){
		WikiSiteGet wsg = new WikiSiteGet();
		String newParents = "";
		for(int i = 0; i<iterations;i++){
			System.out.println("<level" + i + ">");
			Scanner parentScan = new Scanner(parentUrls);
			while(parentScan.hasNext()){
				String parentUrl = parentScan.next();
				String children = wsg.getRefinedWikiLinks(wsg.getWebpageInfo(parentUrl));
				System.out.println("<" + parentUrl + ">\n" + children + "</" + parentUrl + ">");
				Scanner childScan = new Scanner(children);
				newParents = "";
				while(childScan.hasNext()){		
					String childUrl = "http://en.wikipedia.org"+childScan.next();
					newParents += childUrl + "\n";
				}
			}
			System.out.println("</level" + i + ">");
			parentUrls=newParents;			
		}
	}

}
