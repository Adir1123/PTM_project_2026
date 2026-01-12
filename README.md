# PTM Project 2026

This repository contains my implementation of the PTM1 + PTM2 Advanced Software Development project.

## Project Overview
The project implements an event-driven computational graph based on a
Publish-Subscribe architecture.

Agents subscribe to Topics, react to incoming Messages, perform computations,
and publish results to other Topics, forming a directed computational graph.

## Core Concepts
- **Message** – immutable data container with multiple representations
- **Topic** – publish/subscribe channel for message delivery
- **Agent** – reactive component that processes messages and publishes results
- **TopicManager** – thread-safe singleton responsible for managing Topics
- **Graph / Node** – representation of the computational graph with cycle detection
- **Config** – abstraction for building and managing computational configurations

## Implemented Milestones

### Milestone 1
- Immutable Message implementation
- Topic with publish/subscribe mechanism
- Thread-safe lazy Singleton (TopicManager)
- Observer design pattern implementation

### Milestone 2
- Computational graph abstraction
- Node-based cycle detection
- Binary operation agents (BinOpAgent)
- Graph creation from Topics and Agents

### Milestone 3 (In Progress)
- Generic Agents (PlusAgent, IncAgent)
- Config lifecycle management (create / close)
- Preparation for file-based configuration loading

## Technologies & Concepts
- Java
- Object-Oriented Design
- Design Patterns: Observer, Singleton, Strategy
- Thread-safe initialization
- Modular and extensible architecture

---
This project is developed incrementally using Git branches per milestone.
