import cv2
import numpy as np
from flask import Flask, request


app = Flask(__name__)

@app.route('/', methods=['GET', 'POST'])
def index():
    if request.method == 'POST':
        image1 = request.files['image-1']
        image2 = request.files['image-2']
        # format_images ?? im1, im2
        if same_images(im1, im2):
            # format image to answer??
            return im1
        elif resized_images(im1, im2):
            return ('resized', im1, im2)

def same_images(image1, image2):
    if image1.shape != image2.shape:
        return False
    return not np.bitwise_xor(image1,image2).any()

def resized_images(image1, image2):
    im2 = cv.resize(image2, image1.shape)
    return same_images(image1, image2)

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
