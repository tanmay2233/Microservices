# 🧭 Microservice Workflow Visualization

This project visualizes the flow of events between microservices using log data. Each interaction is traced using a **Correlation ID**, and the **timestamp** of events is used to establish the order of service calls.


## 🎯 Objective

To understand the flow of a request across various microservices in a distributed architecture by plotting **directed graphs** based on event logs.

---

## 🔍 How It Works

### 1. **Log Data Format**

Each microservice logs data into a CSV file. The relevant field `value` is a JSON string that contains:
- `correlationId`: A unique identifier for the flow of a single request.
- `timestamp`: The time the event was logged in that service.

**Example `value` field:**

```json
{
  "orderId": "2",
  "status": "INVENTORY_INSUFFICIENT",
  "correlationId": "1c6c8f31-82c4-49e9-8f79-c0fb03d54e43",
  "timestamp": 1746992107510
}
```

## 🔍 Workflow Logic

1. **CSV Files**: 
   - CSV files are read from the `microservices-data/` folder.
   - Each file corresponds to a specific microservice (e.g., `order.csv` → order service).

2. **Log Entry Processing**:
   - For each log entry:
     - The `correlationId` and `timestamp` are extracted from the `value` column.
     - The source service is inferred from the CSV filename.

3. **Grouping Logs**:
   - Logs with the same `correlationId` are grouped together.

4. **Event Sorting and Edge Creation**:
   - For each group:
     - Events are sorted in ascending order of `timestamp`.
     - A **directed edge** is created from one service to the next in the sorted sequence.

