# Assignment3-MD5

## 1. Introduction

This project implements a **distributed MD5 brute-force password cracker** using a **master–worker architecture**.
The system is designed to run on multiple machines (Raspberry Pis) and follows all constraints defined in the assignment.

The goal is to find a **numeric password** whose MD5 hash matches a given target hash.
Only the master node communicates with the teacher’s server via **Java RMI**, while worker nodes perform the brute-force computation.

---

## 2. System Architecture

The system consists of two types of nodes:

### 2.1 Master Node
- Connects to the teacher server using Java RMI
- Registers once using the provided RMI interface
- Receives MD5 cracking challenges
- Splits the search space into disjoint numeric ranges
- Distributes work to worker nodes via TCP sockets
- Collects results and submits the correct solution

### 2.2 Worker Nodes (Raspberry Pis)
- Run independently on different machines
- Listen for TCP connections from the master
- Receive an MD5 hash and a numeric range
- Perform brute-force search on the assigned range
- Return the solution (or `-1` if not found)

Worker nodes never communicate directly with the teacher server.

---

## 3. Communication Model

| Communication | Technology |
|--------------|------------|
Teacher Server ↔ Master Node | Java RMI |
Master Node ↔ Worker Nodes | TCP Sockets |

The teacher server uses RMI callbacks to send problems to the client.
The master node uses TCP sockets to distribute work to the Raspberry Pis.

---

## 4. Work Distribution Strategy

Given:
- `problemSize`: maximum numeric value to test
- `P`: number of worker nodes

The search space `[0, problemSize]` is divided into `P` contiguous, non-overlapping segments, so that each worker processes exactly one segment.

---

## 5. Early Termination and Correctness

To prevent duplicate submissions and incorrect solutions:
- The master node uses an `AtomicBoolean` to ensure that only one solution is submitted
- Once a solution is found, further results from worker nodes are ignored
- This guarantees correctness and prevents point loss due to wrong submissions

---

## 6. Fault Handling

- If a worker node is unreachable or fails during execution, the error is logged
- The system continues running with the remaining workers
- This keeps the system stable during partial failures

---

## 7. How to Run the System

For now there is a class for testing the project (Test.java)

### 7.0 Compile the classes (make sure to be in src folder)

On macOS
```bash
javac */*.java
```

On Windows
```bash
javac executor\*.java rmi\*.java server\*.java utils\*.java
```

### 7.1 Worker Nodes (Raspberry Pis)

On two different terminals, run respectively:

```bash
java executor.WorkerPi
```

and

```bash
java executor.WorkerPi 9001
```


On a third terminal, run:

```bash
java Test
```

For the final project, the master will be run as:

```bash
java rmi.Client
```
