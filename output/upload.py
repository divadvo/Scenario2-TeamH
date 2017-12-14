import requests

with open('best/problem1_3400.solutions', 'rb') as f:
    r = requests.post('http://httpbin.org/post', {'file': f})

print(r.status_code, r.reason)