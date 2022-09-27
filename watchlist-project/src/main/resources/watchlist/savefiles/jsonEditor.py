import json

with open('watchlist-project/src/main/resources/watchlist/savefiles/data.json') as fp:
    data = json.loads(fp.read())
    for movie in data:
        del movie['imdb_url']

with open('watchlist-project/src/main/resources/watchlist/savefiles/movies.json', 'w') as fw:
    json.dump(data, fw, indent=4)