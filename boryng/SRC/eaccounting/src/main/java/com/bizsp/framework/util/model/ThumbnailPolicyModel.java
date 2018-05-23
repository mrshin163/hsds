package com.bizsp.framework.util.model;

import java.io.Serializable;

public class ThumbnailPolicyModel  implements Serializable{

	private static final long serialVersionUID = -2893850198812197681L;

	private String name;

	/**
	 * 자르기여부
	 */
	private boolean isCrop;
	/**
	 * 리사이즈 가로값
	 */
	private int width;
	/**
	 * 리사이즈 세로값
	 */
	private int height;
	/**
	 * 자르기 가로최대값
	 */
	private int maxWidth;
	/**
	 * 자르기 세로비율
	 */
	private double xyRatio;


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isCrop() {
		return isCrop;
	}
	public void setCrop(boolean isCrop) {
		this.isCrop = isCrop;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getMaxWidth() {
		return maxWidth;
	}
	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}
	public double getXyRatio() {
		return xyRatio;
	}
	public void setXyRatio(double xyRatio) {
		this.xyRatio = xyRatio;
	}

}
