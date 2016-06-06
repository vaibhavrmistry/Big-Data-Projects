getwd()
setwd("~/Downloads")
location <- read.csv("ZonedData.csv")
df <- data.frame(long=location$pickup_longitude, lat=location$pickup_latitude)
geo.dist = function(df) {
     require(geosphere)
     d <- function(i,z){         # z[1:2] contain long, lat
         dist <- rep(0,nrow(z))
         dist[i:nrow(z)] <- distHaversine(z[i:nrow(z),1:2],z[i,1:2])
         return(dist)
     }
     dm <- do.call(cbind,lapply(1:nrow(df),d,df))
     return(as.dist(dm))
}
d <- geo.dist(df)
hc <- hclust(d)
df$clust <- cutree(hc,k=XXX)
library(ggplot2)
library(rgdal)
map.US  <- readOGR(dsn="./Big Data Final Project", layer="tl_2013_us_state")
map.CA  <- map.US[map.US$NAME=="New York",]
map.df  <- fortify(map.CA)
ggplot(map.df)+
  geom_path(aes(x=long, y=lat, group=group))+
  geom_point(data=df, aes(x=long, y=lat, color=factor(clust)), size=XX)+
  scale_color_discrete("Cluster")+
  coord_fixed()