EXE=yvision
DESTDIR?=$(HOME)

#OPENCVDIR?=/usr/local/frc
#FRCDIR=/usr/local/frc
OPENCVDIR?=$(HOME)/opencv
FRCDIR=$(HOME)/wpilib/wpilib

FRCINC=$(FRCDIR)/include

COMMONOBJS=csrv.o

.PHONY: clean build install

build: ${EXE}

install:
	sudo install ${EXE} /usr/local/frc/bin
	sudo rm -rf /usr/local/frc/bin/multiCameraServer
	sudo ln -sf /usr/local/frc/bin/${EXE} /usr/local/frc/bin/multiCameraServer

clean:
	rm -f ${EXE} *.o *~

yvision: yvision.o $(COMMONOBJS)
	${CXX} -g -o $@ $^ -L$(OPENCVDIR)/lib -lopencv_calib3d -lopencv_flann -lopencv_imgproc -lopencv_core -lopencv_features2d -lopencv_imgcodecs -L$(FRCDIR)/lib -lcameraserver -lcscore -lntcore -lwpiutil -lwpilibc -lrealsense2 -pthread

.cpp.o:
	${CXX} -g -c -std=c++17 -o $@ -I$(FRCINC)/wpilibc -I$(FRCINC)/wpiutil -I$(FRCINC)/ntcore -I$(FRCINC)/cscore -I$(FRCINC)/cameraserver -I$(OPENCVDIR)/include/opencv4 -I$(PFINC) -I/usr/include//eigen3 $<
