package edu.zhch.nlp.wiki.filter;

import java.util.ArrayList;
import java.util.List;

public class FilterChain implements IFilter {
	
	private List<IFilter> filterList = new ArrayList<IFilter>();
	
	public void addFilter(IFilter filter) {
		filterList.add(filter);
	}
	public boolean doFilter(FilterRequest request) {
		for(IFilter filter : filterList) {
			if(!filter.doFilter(request)) {
				return false;
			}
		}
		return true;
	}
}
