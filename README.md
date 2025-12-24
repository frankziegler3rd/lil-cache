# Overview
## Intention

Most of the work that I've done with Java has been pure Java projects with command-line drivers OR Spring apps that do CRUD. I wanted to do something a little more interesting. Meaning, algorithmic thinking, data structures, design patterns, distribution, networking, etc. This seemed like a good project to get more systems-y. 
## What It Is

This is a mini, and I mean *mini* cache similar to Redis. 

In general, a cache like this is kind of a mini-DB that sits in front of the big DB. Frequently used data can be stored in a cache like this 
to save on DB ops.

Features current and in-progress:
- In-memory K/V store
- TTL eviction
- Eviction policies (LRU, LFU, FIFO)
- Distribution of cache nodes (AWS, Docker)
- Stats (hits, misses, evictions, load times)

# Class Diagram
![LLD](assets/lil-cache-LLD.svg)
# System Design
# Usage
