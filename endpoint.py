import uvicorn
import yadisk
import sys
from fastapi import FastAPI
from fastapi import Request
from multiprocessing import Pipe, JoinableQueue

app = FastAPI()

p1, p2 = Pipe(duplex=False)
jq = JoinableQueue()

y =yadisk.YaDisk("y0_AgAAAAAEnjtHAAh2ZQAAAADQQpFVwaI_1VTaSxKYF4jqPsFnYcvzgHA")
url = y.get_code_url()
homeDirectory = "cdn/"


@app.post("/")
async def endpnt(request: Request):
    data = await request.json()
    p2.send(data)

@app.post("/jq")
async def endpnt(request: Request):
    data = await request.json()
    jq.put(data)

def getPicture(item_path: str):
    print(item_path)
    name = item_path.split(".")
    file = None

    if (name[1] == "mp4"):
        file = y.download(homeDirectory + item_path, 'video.'+name[1])
    if (name[1] == "mp3"):
        file = y.download(homeDirectory + item_path, 'audio.'+name[1])
    if (name[1] == "jpg"):
        file = y.download(homeDirectory + item_path, 'picture.'+name[1])
    if (name[1] == "gif"):
        file = y.download(homeDirectory + item_path, 'gif.'+name[1])

    return file


if __name__ == "__main__":
    uvicorn.run("endpoint:app", host="0.0.0.0", port=8081, log_level="info")
