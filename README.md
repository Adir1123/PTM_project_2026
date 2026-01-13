# PTM Project 2026

This repository contains my implementation of the PTM1 + PTM2 Advanced Software Development project.

## Project Overview
The project implements an event-driven computational system based on a
Publish-Subscribe architecture.

Agents subscribe to Topics, react to incoming Messages, perform computations,
and publish results to other Topics, forming a directed computational graph.

Later milestones extend the system with external interaction capabilities
using a lightweight HTTP interface.

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
- Thread-safe lazy Singleton (`TopicManager`)
- Observer design pattern implementation

### Milestone 2
- Computational graph abstraction
- Node-based cycle detection
- Binary operation agents (`BinOpAgent`)
- Graph creation from Topics and Agents

### Milestone 3
- Generic Agents (`PlusAgent`, `IncAgent`)
- Config lifecycle management (create / close)
- File-based configuration loading using reflection
- Dynamic agent wiring via configuration files
- Introduction of `ParallelAgent` (Decorator, preparation for PTM2)

### Milestone 4
- HTTP request parsing (`RequestParser`)
- Lightweight HTTP server implementation (`MyHTTPServer`)
- Servlet-based request handling
- Longest URI prefix matching
- Integration point for external interaction with the computational graph

## Technologies & Concepts
- Java
- Object-Oriented Design
- Design Patterns: Observer, Singleton, Decorator, Strategy
- Thread-safe initialization
- Modular and extensible architecture
- Basic networking (Sockets, HTTP)

---

Some components were developed with the assistance of AI tools and reviewed and
adapted by the author according to the project requirements.
