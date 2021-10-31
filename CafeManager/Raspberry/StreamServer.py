from flask import Flask
from flask import request
from flask import Response
from flask import stream_with_context

from cctvOpenCV import CctvCamera
import paho.mqtt.publish as publish

app = Flask(__name__)

cctv = CctvCamera()

localHost = "172.1.30.17"

@app.route('/stream')
def stream():
    src = request.args.get('src', default=0, type=int)
    try:
        return Response(
            stream_with_context(stream_gen(src)),
            mimetype='multipart/x-mixed-replace; boundary=frame')

    except Exception as e:
        print('stream error : ', str(e))


def stream_gen(src):
    try:
        cctv.run(src)
        while True:
            frame = cctv.bytesCode()
            yield (b'--frame\r\n'
                   b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n')
            imageByte = cctv.mqttPub()
            if imageByte is not None:
                publish.single("admin/cctv", imageByte, hostname=localHost)
    except GeneratorExit:
        cctv.stop()

# 172.30.1.21:5000/stream?src=-1


if __name__ == '__main__':
    try:
        app.run(host="0.0.0.0", port=5000)

    except KeyboardInterrupt:
        print("종료")