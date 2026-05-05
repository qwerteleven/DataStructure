# Data Structures in Java

A comparative performance benchmark of four classic integer container implementations in Java, each exposing the same interface: `insertar`, `extraer`, `buscar`, `vaciar`, `cardinal`, and `elementos`.

---

## Project Structure

```
datastructure/
├── src/
│   ├── LinkedList/          # Singly linked list container
│   ├── Vector/              # Sorted array container
│   ├── TreeBinary/          # Binary search tree container
│   ├── TreeB/               # B-tree container (disk-backed, configurable order)
│   └── TestOfPerformance/   # Raw benchmark results for the B-tree
```

---

## Data Structures

### [Linked List](https://en.wikipedia.org/wiki/Linked_list) (`LinkedList/`)

A singly linked list of integers. Elements are stored in insertion order (newest first). Duplicate values are rejected on insert.

| Operation | Complexity | Notes |
|---|---|---|
| `insertar` | O(n) | Full scan to check for duplicates before prepending |
| `extraer` | O(n) | Linear scan to find and unlink the node |
| `buscar` | O(n) | Linear scan |
| `cardinal` | O(n) | Traverses the whole list to count |

---

### [Sorted Array / Vector](https://en.wikipedia.org/wiki/Dynamic_array) (`Vector/`)

A fixed-capacity sorted integer array. All operations use [binary search](https://en.wikipedia.org/wiki/Binary_search_algorithm) for lookups; insertion and deletion shift elements to maintain order.

| Operation | Complexity | Notes |
|---|---|---|
| `insertar` | O(log n) search + O(n) shift | Binary search to find position, then `arraycopy` |
| `extraer` | O(log n) search + O(n) shift | Binary search then `arraycopy` to close the gap |
| `buscar` | O(log n) | Pure binary search |
| `cardinal` | O(1) | Maintained as a field |

Capacity is fixed at construction time; `insertar` returns `false` when full.

---

### [Binary Search Tree](https://en.wikipedia.org/wiki/Binary_search_tree) (`TreeBinary/`)

An unbalanced in-memory BST. Duplicates are rejected. Deletion uses the [in-order successor](https://en.wikipedia.org/wiki/Binary_search_tree#Deletion) (symmetric successor) strategy for nodes with two children. `elementos()` performs an [in-order traversal](https://en.wikipedia.org/wiki/Tree_traversal#In-order_(LNR)) and returns elements in sorted order.

| Operation | Complexity | Notes |
|---|---|---|
| `insertar` | O(h) | h = tree height; O(log n) average, O(n) worst case |
| `extraer` | O(h) | Symmetric-successor deletion |
| `buscar` | O(h) | Standard BST search |
| `cardinal` | O(1) | Counter maintained on insert/extract |

---

### [B-tree](https://en.wikipedia.org/wiki/B-tree) (`TreeB/`)

A disk-backed B-tree with configurable order (minimum order 5). Nodes are serialized to a binary file via `FicheroAyuda` and `Conversor` helpers. The tree handles [node splitting](https://en.wikipedia.org/wiki/B-tree#Insertion), [rotation](https://en.wikipedia.org/wiki/Tree_rotation), and [merging](https://en.wikipedia.org/wiki/B-tree#Deletion) during insertions and deletions. Within each node, keys are searched with [binary search](https://en.wikipedia.org/wiki/Binary_search_algorithm).

| Operation | Complexity | Notes |
|---|---|---|
| `insertar` | O(log n) | Splits nodes when full; may promote a key to the parent |
| `extraer` | O(log n) | Rebalances via rotation or merge when underflowing |
| `buscar` | O(log n) | Traverses from root; binary search within each node |
| `cardinal` | O(1) | Stored in the file header |

**Order tuning:** The order controls the number of keys per node. `crear(path, order)` initialises a new tree file; `abrir(path)` reopens an existing one.

---

## Common Interface

Every container implements the same set of operations:

```java
int     cardinal()           // number of elements currently stored
boolean insertar(int e)      // add e; returns false if already present (or full)
boolean extraer(int e)       // remove e; returns false if not found
boolean buscar(int e)        // returns true if e is in the container
void    vaciar()             // remove all elements
int[]   elementos()          // return all elements in ascending order
```

---

## Performance Benchmarks

Each `PruebaContenedor` class runs four timed tests against two data files:

| Test | Description |
|---|---|
| **Test 1 – Insertions** | 10 batches of 10,000 sequential inserts; time per 1,000 ops |
| **Test 2 – Extractions** | 10 batches of 10,000 deletes on the same data; time per 1,000 ops |
| **Test 3 – Successful search** | Growing dataset (10k–100k), searching only keys that exist |
| **Test 4 – Unsuccessful search** | Same sizes, searching only keys that are absent (`datos_no.dat`) |

Results are written to `salida1.txt` – `salida4.txt`. Raw B-tree results across orders 5–301 are recorded in `TestOfPerformance/TiemposTreeB`.

### B-tree order vs. performance summary

Based on the recorded results, order **25** offers the best overall balance — insertion and extraction times bottom out around orders 20–25, and search times are near their minimum. Very low orders (5–9) have higher overhead due to frequent splits and merges; very high orders (201–301) degrade because linear scans within oversized nodes outweigh the shallower tree depth.

---

## Data Files

| File | Contents |
|---|---|
| `datos.dat` | 100,000 sequential (sorted) integers as raw 4-byte big-endian ints |
| `datos_no.dat` | 20,000 random integers guaranteed not to appear in `datos.dat` |

Generate them with a fixed random seed so results are reproducible across runs.

---

## Requirements & Build

- Java JDK 8 or above
- No external dependencies

Compile and run each package independently. For the B-tree, `crear()` must be called before the first use to initialise the backing file.

```bash
javac src/Vector/*.java && java -cp src Vector.PruebaContenedor
javac src/LinkedList/*.java && java -cp src LinkedList.PruebaContenedor
javac src/TreeBinary/*.java && java -cp src TreeBinary.PruebaContenedor
javac src/TreeB/*.java && java -cp src TreeB.PruebaContenedor
```

---

## License

MIT — see [LICENSE](LICENSE).