import pandas as pd
import networkx as nx
import matplotlib.pyplot as plt
import glob
import json
import os

file_paths = glob.glob("./microservices-data/*.csv")
all_events = []

for file in file_paths:
    df = pd.read_csv(file, delimiter=';')

    df['service'] = os.path.basename(file).split(".")[0]

    def extract_fields(value):
        try:
            data = json.loads(value)
            return pd.Series({
                'correlationId': data.get("correlationId"),
                'event_timestamp': data.get("timestamp")
            })
        except json.JSONDecodeError:
            return pd.Series({'correlationId': None, 'event_timestamp': None})

    extracted_fields = df['value'].apply(extract_fields)
    df = pd.concat([df, extracted_fields], axis=1)

    df.dropna(subset=['correlationId', 'event_timestamp'], inplace=True)

    all_events.append(df)

merged_df = pd.concat(all_events, ignore_index=True)

grouped = merged_df.groupby('correlationId')

for corr_id, group in grouped:
    sorted_group = group.sort_values(by='event_timestamp')
    services = list(sorted_group['service'])

    edges = []
    for i in range(len(services) - 1):
        edges.append((services[i], services[i + 1]))

    edges.append(("order-topic", "inventory-topic"))

    if not edges:
        continue

    G = nx.DiGraph()
    G.add_edges_from(edges)

    plt.figure(figsize=(8, 5))
    pos = nx.spring_layout(G, seed=42)
    nx.draw(G, pos, with_labels=True, node_color='lightblue', node_size=2000, font_size=12, arrowsize=20)
    plt.title(f"Workflow for Correlation ID: {corr_id}")
    plt.tight_layout()
    plt.show()