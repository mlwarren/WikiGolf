

public class WikiSite {
	private String siteURL;
	private int levelDeepness;
	private WikiSite parent;
	private String children;
	
	public WikiSite(String url, WikiSite parent, String children){
		this.siteURL = url;
		this.parent = parent;
		this.children = children;
	}
	
	public boolean hasParent(){
		if(this.parent!=null){
			return true;
		}
		return false;
	}
	
	public WikiSite getParent(){
		if(hasParent()){
			return this.parent;
		}
		System.err.println("Current site has no parent.");
		return null;
	}
	
	public String getChildren(){
		return this.children;
	}
	
	public int getLevelDeepness(){
		if(this.parent==null){
			return 0;
		}
		else{
			return 1 + this.parent.getLevelDeepness(); //recursively get level deepness
		}
	}
	
	public WikiSite[] chainBack(){
		int deepness = getLevelDeepness();
		
		WikiSite ws[] = new WikiSite[deepness]; //create array of WikiSites of length deepness 
		WikiSite current = new WikiSite(this.siteURL, this.parent, this.children); //make current the current wikisite
		ws[deepness] = current; //set last element in array to current wikisite
		
		for(int i=1; i<deepness; i++){ //populate array with each level, going back to root
			//lets make the first node the parent, followed by each in the path
			current=current.getParent();
			ws[deepness-i]=current;
		}
		return ws;
	}
	
}
