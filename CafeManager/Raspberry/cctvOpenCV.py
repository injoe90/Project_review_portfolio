import RPi.GPIO as GPIO
import time

import cv2
import numpy
import imutils

from threading import Thread
from queue import Queue

import paho.mqtt.publish as publish


class CctvCamera:

    # 초기 선언
    def __init__(self):

        GPIO.setmode(GPIO.BCM)
        PIR_PIN = 26
        GPIO.setup(PIR_PIN, GPIO.IN)

        if cv2.ocl.haveOpenCL():
            cv2.ocl.setUseOpenCL(True)
        print('OpenCL: ', cv2.ocl.haveOpenCL())

        self.capture = None
        self.thread = None
        self.started = False
        self.preview_time = time.time()
        self.current_time = time.time()
        self.queue = Queue(maxsize=128)

        self.pinNum = 26
        self.enterTime = 0
        self.imgByte = None
        self.hostName = "172.1.30.21"

        self.xml = 'haarcascade_frontalface_default.xml'
        self.face_cascade = cv2.CascadeClassifier(self.xml)
        self.gray = None

        self.stat = False
        self.sec = 0

    # 영상 촬영 시작
    def run(self, src=0):
        self.stop()
        self.capture = cv2.VideoCapture(src)

        self.capture.set(cv2.CAP_PROP_FRAME_WIDTH, 320)
        self.capture.set(cv2.CAP_PROP_FRAME_HEIGHT, 240)

        if self.thread is None:
            self.thread = Thread(target=self.update, args=())
            self.thread.daemon = False
            self.thread.start()
        self.started = True

    # 영상 촬영 정지
    def stop(self):
        self.started = False

        if self.capture is not None:
            self.capture.release()
            self.clear()

    # 영상 데이터 삭제
    def clear(self):
        with self.queue.mutex:
            self.queue.queue.clear()

    # 영상 데이터 실시간 처리
    def update(self):
        while True:
            if self.started:
                success, frame = self.capture.read()

                if success:
                    self.gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

                    # 영상 프레임에서 얼굴 인식하기
                    faces = self.face_cascade.detectMultiScale(self.gray, 1.1, 5)

                    # 인식한 얼굴에 사각형 그리기
                    for x, y, w, h in faces:
                        cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 255, 0), 4, cv2.LINE_4)

                    # PIR 센서가 감지하고 얼굴을 인식했을 때에만 사진을 저장하고 네트워크로 모바일 앱에 전달하기
                    if len(faces) != 0 and GPIO.input(self.pinNum) == GPIO.HIGH and self.enterTime == 0:
                        cv2.imwrite("Entrance/test.jpg", frame)
                        imgFile = open("Entrance/test.jpg", "r+b")
                        imgFileRead = imgFile.read()
                        self.imgByte = bytearray(imgFileRead)
                        self.enterTime = 1

                    self.queue.put(frame)

    # flask 웹 서버에서 실행할 메서드
    # 영상을 이진 코드로 전환
    def bytesCode(self):
        if not self.capture.isOpened():
            frame = self.blank()
        else:
            frame = imutils.resize(self.read(), width=320)

            if self.stat:
                cv2.rectangle(frame, (0, 0), (120, 30), (0, 0, 0), -1)
                fps = 'FPS : ' + str(self.fps())
                cv2.putText(frame, fps, (10, 20), cv2.FONT_HERSHEY_PLAIN, 1, (0, 0, 255), 1, cv2.LINE_AA)
        return cv2.imencode('.jpg', frame)[1].tobytes()

    # 영상 데이터가 없을 때에 대한 이벤트 처리
    @staticmethod
    def blank():
        return numpy.ones(shape=[320, 240, 3], dtype=numpy.uint8)

    # 영상 데이터 읽기
    def read(self):
        return self.queue.get()

    # 영상 FPS 처리
    def fps(self):
        self.current_time = time.time()
        self.sec = self.current_time - self.preview_time
        self.preview_time = self.current_time

        if self.sec > 0:
            fps = round(1 / self.sec, 1)
        else:
            fps = 1
        return fps

    def mqttPub(self):
        return self.imgByte

    # 클래스 종료
    def __exit__(self):
        self.capture.relase()
