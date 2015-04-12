import json
from datetime import datetime
filename = open("filename","r")

objlist = {}
objlist['results'] = []
for line in filename:
    # print(line)
    srt = open(line.strip(),"r")
    idx = 0
    obj = {}
    no = 1
    subtitle = ""
    for l in srt:
        tmp = l.strip()
        if tmp == "":
            continue
        else:
            if tmp == str(no):
                if bool(obj):
                    obj['subtitle'] = subtitle
                    subtitle = ""
                    objlist['results'].append(obj)
                    obj = {}
                obj['no'] = tmp
                yo = line.strip()
                yo = yo[0:len(yo)-4]
                obj['filename'] = yo
                no += 1
                idx += 1
            elif idx % 2 == 1:
                tmp = tmp.split(' ')
                obj['timeStart'] = tmp[0]
                obj['timeEnd'] = tmp[2]
                idx += 1
            elif idx % 2 == 0:
                subtitle += tmp
    if bool(obj):
        obj['subtitle'] = subtitle
        objlist['results'].append(obj)
    srt.close()
json1 = json.dumps(objlist,ensure_ascii=False)
print(json1)
