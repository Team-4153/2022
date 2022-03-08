#ifndef _CSRV_H
#define _CSRV_H

#include <opencv2/core/core.hpp>
#include <opencv2/imgproc.hpp>
#include <networktables/NetworkTableInstance.h>

struct Camera {
	std::string	name;
	std::string	path;
	int		width;
	int		height;

	// vision target tracking
	bool		targetTrack;
	int		targetHLow, targetSLow, targetVLow;
	int		targetHHigh, targetSHigh, targetVHigh;

	// ball target tracking
	bool		ballTrack;
	int		ballSLow, ballVLow;
	int		ballSHigh, ballVHigh;
	double		ballMinArea, ballMaxArea;

	// saving images
	int		savePeriod;	// in ms, 0 == disable

};

struct Target {
	static std::vector<cv::Point3f> model;
	static cv::Mat cameraMatrix;

	cv::Rect rect;

//	cv::Point2f poly[4];	// order: top-left, bottom-left, bottom-right, top-right

	Target(cv::Rect &r);
	~Target();

	void draw(cv::Mat &dst, int idx, int w);
	double centerX();
	double centerY();
	double width();
	double height();
};

enum {
	BallRed	= 1,
	BallBlue = 2,
};

struct Ball {
	cv::Point2f center;
	double radius;
	int color;

	Ball(cv::Point2f& c, double r, int clr):center(c), radius(r), color(clr) {};
};

extern nt::NetworkTableInstance ntinst;

int csrvInit(void (*frameCallback)(const Camera& c, uint64_t timestamp, cv::Mat frame),
	void (*targetsCallback)(const Camera& c, uint64_t timestamp, Target *center));

#endif
