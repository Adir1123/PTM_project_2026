# PTM Project 2026

This repository contains my solutions for the PTM1 + PTM2 advanced software development project.

---

## Milestone 1 – Core Messaging Infrastructure

### Overview
This milestone implements the core infrastructure of a publish–subscribe messaging system.

### Implemented Components

#### Message
- Immutable message object
- Stores raw data (`byte[]`), text representation and numeric representation (`double`)
- Automatically records creation time

#### Agent
- Interface representing a software agent
- Agents can subscribe to topics and react to incoming messages via callback

#### Topic
- Represents a publish–subscribe subject
- Implements the Observer design pattern
- Manages subscribers and publishers
- Publishes messages to all subscribed agents

#### TopicManagerSingleton
- Manages creation and access to Topic instances
- Ensures a single TopicManager instance (Singleton)
- Uses lazy and thread-safe initialization via a static inner class
- Provides Flyweight-style topic retrieval (`getTopic`)

### Design Patterns Used
- **Observer Pattern** – Topic notifies subscribed Agents
- **Singleton Pattern (lazy & thread-safe)** – TopicManagerSingleton
- **Flyweight-like behavior** – Shared Topic instances by name
