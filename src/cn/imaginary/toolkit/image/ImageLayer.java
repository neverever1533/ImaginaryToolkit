package cn.imaginary.toolkit.image;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;

import java.io.File;

import java.util.Properties;

import javaev.awt.DimensionUtils;
import javaev.awt.PointUtils;
import javaev.imageio.ImageIOUtils;

import javaev.lang.ObjectUtils;

public class ImageLayer {
	private float alphaLayer = 1.0f;

	private PointUtils anchorLayer;

	private int depthLayer = -1;

	private double gravityLayer;

	private ImageIOUtils imageIOUtils = new ImageIOUtils();

	private BufferedImage imageLayer;

	private String imagePathLayer;

	private boolean isAlphaLayer;
	private boolean isGravityLayer;
	private boolean isVisibleLayer;

	private PointUtils locationLayer;

	private ObjectUtils objectUtils = new ObjectUtils();

	private PointUtils rootLayer;

	private double rotateLayer;

	private DimensionUtils scaleLayer;
	private DimensionUtils sizeLayer;

	private String tag_alpha = "alpha";
	private String tag_anchor = "anchor";
	private String tag_depth = "depth";
	private String tag_gravity = "gravity";
	private String tag_imagePath = "imagePath";
	private String tag_isAlpha = "isAlpha";
	private String tag_isGravity = "isGravity";
	private String tag_isVisible = "isVisible";
	private String tag_location = "location";
	private String tag_root = "root";
	private String tag_rotated = "rotated";
	private String tag_scale = "scale";
	private String tag_size = "size";

	public float getAlpha() {
		return alphaLayer;
	}

	public PointUtils getAnchor() {
		return anchorLayer;
	}

	public int getDepth() {
		return depthLayer;
	}

	public double getGravity() {
		return gravityLayer;
	}

	public BufferedImage getImage() {
		return imageLayer;
	}

	public String getImagePath() {
		return imagePathLayer;
	}

	public PointUtils getLocation() {
		return locationLayer;
	}

	public Properties getProperties() {
		Properties properties = new Properties();
		properties.put(tag_alpha, getAlpha());
		PointUtils anchor = getAnchor();
		if (null != anchor) {
			properties.put(tag_anchor, anchor);
		}
		properties.put(tag_depth, getDepth());
		properties.put(tag_gravity, getGravity());
		String imagePath = getImagePath();
		if (null != imagePath) {
			properties.put(tag_imagePath, imagePath);
		}
		PointUtils location = getLocation();
		if (null != location) {
			properties.put(tag_location, location);
		}
		properties.put(tag_rotated, getRotated());
		DimensionUtils scale = getScale();
		if (null != scale) {
			properties.put(tag_scale, scale);
		}
		DimensionUtils size = getSize();
		if (null != size) {
			properties.put(tag_size, size);
		}
		PointUtils root = getRoot();
		if (null != root) {
			properties.put(tag_root, root);
		}
		properties.put(tag_isAlpha, isAlpha());
		properties.put(tag_isGravity, isGravity());
		properties.put(tag_isVisible, isVisible());
		return properties;
	}

	public PointUtils getRoot() {
		return rootLayer;
	}

	public double getRotated() {
		return rotateLayer;
	}

	public DimensionUtils getScale() {
		return scaleLayer;
	}

	public DimensionUtils getSize() {
		return sizeLayer;
	}

	public boolean isAlpha() {
		return isAlphaLayer;
	}

	public void isAlpha(boolean isAlpha) {
		isAlphaLayer = isAlpha;
	}

	public boolean isGravity() {
		return isGravityLayer;
	}

	public void isGravity(boolean isGravity) {
		if (isGravityLayer != isGravity) {
			isGravityLayer = isGravity;
		}
	}

	public boolean isVisible() {
		return isVisibleLayer;
	}

	public void isVisible(boolean isVisible) {
		if (isVisibleLayer != isVisible) {
			isVisibleLayer = isVisible;
		}
	}

	public void read(File file) {
		if (null != file) {
			BufferedImage image = imageIOUtils.read(file);
			if (null != image) {
				setImage(image);
				setImagePath(file.getPath());
			}
		}
	}

	public void read(String filePath) {
		if (null != filePath) {
			read(new File(filePath));
		}
	}

	public void scale(Dimension scale) {
		scale(new DimensionUtils(scale));
	}

	public void scale(DimensionUtils scale) {
		scaleLayer = scale;
	}

	public void scale(double scaleWidth, double scaleHeight) {
		DimensionUtils dimension = new DimensionUtils();
		dimension.setSize(scaleWidth, scaleHeight);
		scale(dimension);
	}

	public void setAlpha(float alpha) {
		if (alpha < 0 || alpha > 1) {
			return;
		}
		alphaLayer = alpha;
		if (alphaLayer == 1) {
			isAlpha(false);
		} else {
			isAlpha(true);
		}
	}

	public void setAnchor(double ax, double ay) {
		PointUtils point = new PointUtils();
		point.setLocation(ax, ay);
		setAnchor(point);
	}

	public void setAnchor(PointUtils anchor) {
		anchorLayer = anchor;
	}

	public void setAnchor(Point anchor) {
		setAnchor(new PointUtils(anchor));
	}

	public void setDepth(int depth) {
		depthLayer = depth;
	}

	public void setGravity(double angle) {
		gravityLayer = angle % 360;
	}

	public void setImage(BufferedImage image) {
		if (null != image) {
			imageLayer = image;
			isVisible(true);
			setLocation(0, 0);
			setSize(image.getWidth(), image.getHeight());
		} else {
			isVisible(false);
			PointUtils point = null;
			setLocation(point);
			DimensionUtils dimension = null;
			setSize(dimension);
		}
		isAlpha(false);
	}

	public void setImagePath(String filePath) {
		imagePathLayer = filePath;
	}

	public void setLocation(double tx, double ty) {
		PointUtils point = new PointUtils();
		point.setLocation(tx, ty);
		setLocation(point);
	}

	public void setLocation(Point location) {
		setLocation(new PointUtils(location));
	}

	public void setLocation(PointUtils location) {
		locationLayer = location;
	}

	public void setProperties(Properties properties) {
		if (null != properties) {
			Object object;
			Object value;
			String value_String;
			value = properties.get(tag_imagePath);
			if (null != value) {
				value_String = String.valueOf(value);
				read(value_String);
			}
			value = properties.get(tag_size);
			if (null != value) {
				if (value instanceof DimensionUtils) {
					setSize((DimensionUtils) value);
				} else if (value instanceof Dimension) {
					setSize((Dimension) value);
				} else {
					value_String = String.valueOf(value);
					object = objectUtils.getObject(value_String);
					if (null != object && object instanceof DimensionUtils) {
						setSize((DimensionUtils) object);
					}
					if (null != object && object instanceof Dimension) {
						setSize((Dimension) object);
					}
				}
			}
			value = properties.get(tag_alpha);
			if (null != value) {
				if (value instanceof Number) {
					setAlpha((float) value);
				} else {
					value_String = String.valueOf(value);
					object = objectUtils.getObject(value_String);
					if (null != object && object instanceof Number) {
						setAlpha(Float.valueOf(value_String));
					}
				}
			}
			value = properties.get(tag_anchor);
			if (null != value) {
				if (value instanceof PointUtils) {
					setAnchor((PointUtils) value);
				} else if (value instanceof Point) {
					setAnchor((Point) value);
				} else {
					value_String = String.valueOf(value);
					object = objectUtils.getObject(value_String);
					if (null != object && object instanceof PointUtils) {
						setAnchor((PointUtils) object);
					}
					if (null != object && object instanceof Point) {
						setAnchor((Point) object);
					}
				}
			}
			value = properties.get(tag_gravity);
			if (null != value) {
				if (value instanceof Number) {
					setGravity((double) value);
				} else {
					value_String = String.valueOf(value);
					object = objectUtils.getObject(value_String);
					if (null != object && object instanceof Number) {
						setGravity((double) object);
					}
				}
			}
			value = properties.get(tag_isAlpha);
			if (null != value) {
				if (value instanceof Boolean) {
					isAlpha((boolean) value);
				} else {
					value_String = String.valueOf(value);
					object = objectUtils.getObject(value_String);
					if (null != object && object instanceof Boolean) {
						isAlpha((boolean) object);
					}
				}
			}
			value = properties.get(tag_isGravity);
			if (null != value) {
				if (value instanceof Boolean) {
					isGravity((boolean) value);
				} else {
					value_String = String.valueOf(value);
					object = objectUtils.getObject(value_String);
					if (null != object && object instanceof Boolean) {
						isGravity((boolean) object);
					}
				}
			}
			value = properties.get(tag_isVisible);
			if (null != value) {
				if (value instanceof Boolean) {
					isVisible((boolean) value);
				} else {
					value_String = String.valueOf(value);
					object = objectUtils.getObject(value_String);
					if (null != object && object instanceof Boolean) {
						isVisible((boolean) object);
					}
				}
			}
			value = properties.get(tag_depth);
			if (null != value) {
				if (value instanceof Number) {
					setDepth((int) value);
				} else {
					value_String = String.valueOf(value);
					object = objectUtils.getObject(value_String);
					if (null != object && (object instanceof Number)) {
						setDepth(Integer.valueOf(value_String));
					}
				}
			}
			value = properties.get(tag_location);
			if (null != value) {
				if (value instanceof PointUtils) {
					setLocation((PointUtils) value);
				} else if (value instanceof Point) {
					setLocation((Point) value);
				} else {
					value_String = String.valueOf(value);
					object = objectUtils.getObject(value_String);
					if (null != object && object instanceof PointUtils) {
						setLocation((PointUtils) object);
					} else if (null != object && object instanceof Point) {
						setLocation((Point) object);
					}
				}
			}
			value = properties.get(tag_root);
			if (null != value) {
				if (value instanceof PointUtils) {
					setRoot((PointUtils) value);
				} else if (value instanceof Point) {
					setRoot((Point) value);
				} else {
					value_String = String.valueOf(value);
					object = objectUtils.getObject(value_String);
					if (null != object && object instanceof PointUtils) {
						setRoot((PointUtils) object);
					} else if (null != object && object instanceof Point) {
						setRoot((Point) object);
					}
				}
			}
			value = properties.get(tag_rotated);
			if (null != value) {
				if (value instanceof Number) {
					setRotated((double) value);
				} else {
					value_String = String.valueOf(value);
					object = objectUtils.getObject(value_String);
					if (null != object && object instanceof Number) {
						setRotated((double) object);
					}
				}
			}
			value = properties.get(tag_scale);
			if (null != value) {
				if (value instanceof DimensionUtils) {
					scale((DimensionUtils) value);
				} else if (value instanceof Dimension) {
					scale((Dimension) value);
				} else {
					value_String = String.valueOf(value);
					object = objectUtils.getObject(value_String);
					if (null != object && object instanceof DimensionUtils) {
						scale((DimensionUtils) object);
					} else if (null != object && object instanceof Dimension) {
						scale((Dimension) object);
					}
				}
			}
		}
	}

	public void setRoot(double rx, double ry) {
		PointUtils point = new PointUtils();
		point.setLocation(rx, ry);
		setRoot(point);
	}

	public void setRoot(Point root) {
		setRoot(new PointUtils(root));
	}

	public void setRoot(PointUtils root) {
		rootLayer = root;
	}

	public void setRotated(double angle) {
		angle = angle % 360;
		rotateLayer = angle;
	}

	public void setSize(Dimension dimension) {
		setSize(new DimensionUtils(dimension));
	}

	public void setSize(DimensionUtils dimension) {
		if (null != dimension) {
			double scaleWidth;
			double scaleHeight;
			if (null != sizeLayer) {
				scaleWidth = dimension.getWidth() / sizeLayer.getWidth();
				scaleHeight = dimension.getHeight() / sizeLayer.getHeight();
			} else {
				scaleWidth = 1;
				scaleHeight = 1;
			}
			scale(scaleWidth, scaleHeight);
			double x = dimension.getWidth() / 2;
			double y = dimension.getHeight() / 2;
			setAnchor(x, y);
		} else {
			PointUtils point = null;
			setAnchor(point);
		}
		setRoot(getAnchor());
		sizeLayer = dimension;
	}

	public void setSize(double width, double height) {
		DimensionUtils dimension = new DimensionUtils();
		dimension.setSize(width, height);
		setSize(dimension);
	}
}
