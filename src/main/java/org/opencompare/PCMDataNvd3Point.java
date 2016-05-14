package org.opencompare;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
class PCMDataNvd3Point implements Serializable{
	public Object x;
	public Object y;
	public Object color;
	public Object size;
	
	public PCMDataNvd3Point(Object x, Object y, Object color, Object size) {
		super();
		this.x = x;
		this.y = y;
		this.color = color;
		this.size = size;
	}
}
