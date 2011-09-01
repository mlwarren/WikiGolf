

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.regex.*;

/**
 * This class handles getting the wiki site and processing the link information.
 * @author mlwarren
 *
 */
public class WikiSiteGet {
	
	/**
	 * For a specific site, this method will get all the links from the html.
	 * USED FOR DEBUGGING
	 * @param in html site code in
	 * @return returns a list of links in <a href = " " > form
	 */
	public String getAllLinks(String in){
		Pattern p = Pattern.compile("<a\\s*href\\s*=\\s*\"[^>]*>\\s*");
		Matcher m1 = p.matcher(in);
		
		boolean isMatcherTrue = m1.find();
		
		String whatMatched = "";
		String out = "";
		while(isMatcherTrue){
			whatMatched = m1.group();
			out += whatMatched + "\n";
			isMatcherTrue = m1.find();
		}
		return out;
	}
	
	/**
	 * This method will refine the links from the html code of a page. They will look like 
	 * "/wiki/stuff". Doesn't necessarily need the getAllLinks method to be run first.
	 * @param in html code in
	 * @return refined links out
	 */
	public String getRefinedWikiLinks(String in){
		Pattern p = Pattern.compile("\"/wiki/[^\"\\$:\\.]*\"");
		Matcher m1 = p.matcher(in);
		
		boolean isMatcherTrue = m1.find();
		
		String whatMatched = "";
		String out = "";
		while(isMatcherTrue){
			whatMatched = m1.group();
			if(!whatMatched.equals("\"/wiki/Main_Page\"")){
				out += whatMatched + "\n";
			}
			isMatcherTrue = m1.find();
		}
		out = removeQuotes(out);
		return out;
	}
	
	public String getWebsiteTitle(String in){
		in = in.substring(28,in.length());
		return in;
	}
	
	/**
	 * Possibly implement this in the future
	 * @param in
	 * @return
	 */
	public String removeDuplicateLinks(String in){
		String out = "";
		return out;
	}
	
	/**
	 * Removes quotes from wiki links. "/wiki/stuff" becomes /wiki/stuff
	 * @param in The links to remove quotes from
	 * @return Links with removed quotes
	 */
	public String removeQuotes(String in){
		Scanner scan = new Scanner(in);
		String line = "";
		String out = "";
		while(scan.hasNext()){
			line = scan.next();
			line = line.substring(1,line.length()); //removes first quote
			line = line.substring(0, line.length()-1); //removes last quote
			out += line + "\n";
		}
		return out;
	}
	
	/**
	 * Gets the website's html info and makes it human readable.
	 * USED FOR DEBUGGING
	 * @param webText html in
	 * @return readable html out
	 */
	public String humanReadable(String webText){
		Scanner scan = new Scanner(webText);
	    scan.useDelimiter(">");
	    String out = "";
	    while(scan.hasNext()){
	    	out += scan.next() + ">\n";
	    }
		return out;
	}
	
	
	/**
	 * Get the webpage, basically a wget that'll work in java.
	 * @param url The url of the page
	 * @return html code of the page
	 */
	public String getWebpageInfo(String url){

		URL u;
    	InputStream is = null;
    	String s;
    	String out="";
    
    	try{
    	u = new URL(url);
      	is = u.openStream();
      	BufferedReader br = new BufferedReader(new InputStreamReader(is));
      	while ((s = br.readLine()) != null){
      		out+=s;
      		}
    	}
    	
    	catch (MalformedURLException e){
    		System.err.println("MalformedURLException: " + e);
    		e.printStackTrace();
    		System.exit(2);
    	}
    	
    	catch (IOException e){
    		System.err.println("IOException: " + e);
    		e.printStackTrace();
    		System.exit(3);
    	}
    	finally
    	{
    	try{
    	  is.close();
      	}
      	catch (IOException ioe){
      		}
    	}
    	return out;
	}
	
}
