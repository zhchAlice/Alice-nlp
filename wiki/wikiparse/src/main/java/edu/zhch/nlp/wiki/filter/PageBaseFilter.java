package edu.zhch.nlp.wiki.filter;

public class PageBaseFilter implements IFilter {

	public boolean doFilter(FilterRequest request) {
		if(request.getInputWords().size() < FilterConstants.WORD_MIN_LIMIT 
				||request.getOutLinks().size() < FilterConstants.LINK_MIN_LIMIT) {
			return false;
		}
		return true;
	}

}
