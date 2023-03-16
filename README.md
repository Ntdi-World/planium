# Planium
Planium is an API to add text onto an image. This API is meant for you to use as it comes with lots of features. Images are cached, so don't worry about spamming the API. Planium is hosted on an Oracle Cloud (OCI) server with ARM64 based processing. The current cache limit is 100,000 unique photos, if there becomes a point where that is too little, then I will upgrade the server, but for now, this should do. 

You can check out the website here [https://planium.ntdi.world](https://planium.ntdi.world)

# Understanding how it works
Planium, whilist alittle hacky, works in a super efficient way.

## Caching images
When you request an Image for the first time, Planium will generate it as normal, but if you've already requested this same Image, Planium will automatically serve you the already generated one. Planium is able to do this by caching its images, but I don't want to use up all my storage to hold all your images!

### Invalidating the Cache
Planium has a max storage limit of 100,000 unique photos, but once that limit is reached, how will Planium know which image to remove to make more space for the next unique image? Now you may thing removing the oldest image would be the easiest and simplest... ...you'd be right. Lucky for me I like a challenge, so the cache invalidation method that I implemented into Planium is called Least-Frequenty-Used or LFU. LFU works by sorting the images based on how many times they've been requested. Then once Planium needs to remove an image, it just deletes the lowest ranking one. 

## Generating images
Surprisingly, the easiest part was making the cache, generating the text onto the image is also simple, but the hardest part comes down to storing it.

### Pitfalls
Originally I had tried to put the images into the static folder and serve them with Spring's native MVC, but that doesn't exactly work when running the application from a Jar. 

### Solution
I was able to figure out that you can modify the HTTP headers when returning a request with spring and with that, I can return an image from an outside folder! This is game changing as I no longer need to rely on the resources folder for hosting images, and instead I can just serialize them and serve them to the user when requested.

## Encoding/Serializing images
Originally, I had tried making my own method with putting all the request parameters together and throwing it all into a file, but when you request text with Planium that contains special characters like &%$#, well, file systems don't exactly like that..

### Encryption Algorithms
Now I had the brilliant idea of using Base64 to encode the request parameters put together but with a alphabetical charset! Until I learned Base64 also uses a 64 + 1 charset, e.g. +/=

That's cool and all, until you need to save it on a file system, so I discovered Base58 which basically just takes all of that out of Base64! Base58 is also used to store crypto addresses like bitcoin or eth, so it fit pretty well!

 