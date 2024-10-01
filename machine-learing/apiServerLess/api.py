from flask import Flask, request, jsonify
from transformers import pipeline
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np
import torch
device = 0 if torch.cuda.is_available() else -1
app = Flask(__name__)

model = pipeline('feature-extraction', model='sentence-transformers/all-mpnet-base-v2' ,device=device ,  batch_size=32)


@app.route('/api/all-mpnet-base-v2', methods=['POST'])
def get_embedding():
    # Nhận dữ liệu đầu vào
    data = request.json
    source_sentence = data.get('inputs', {}).get('source_sentence', '')
    sentences = data.get('inputs', {}).get('sentences', [])

    # Tạo nhúng cho câu nguồn và danh sách các câu
    source_embedding = model(source_sentence)
    sentence_embeddings = model(sentences)

    # Chuyển đổi nhúng thành mảng NumPy và lấy trung bình
    source_embedding = np.array(source_embedding).mean(axis=1)  # Nhúng của câu nguồn
    result = []
    for sentence in sentence_embeddings:
        result.append(np.array(sentence).mean(axis=1))  # Nhúng của từng câu

    similarity = []
    for item in result:
        similarity.append(cosine_similarity(source_embedding.reshape(1, -1), item))

    similarity = np.array(similarity).flatten()
    return jsonify(
        similarity.tolist()
    )


if __name__ == '__main__':
    app.run(port=5000)
