# -*- coding:utf-8 -*-
# python hello.py
#不要用spyder运行，要不然结束不了。用命令行运行python
from flask import Flask
app = Flask(__name__)

@app.route('/')
def hello_world():
    return 'Hello World!'

if __name__ == '__main__':
    app.run()