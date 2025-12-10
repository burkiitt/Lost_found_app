from flask import Flask, request, jsonify
from sentence_transformers import SentenceTransformer, util
from PIL import Image
import io
import torch

app = Flask(__name__)

# Загружаем CLIP (работает на CPU)
model = SentenceTransformer("clip-ViT-B-32")

# ============== 1. Получить embedding картинки ==============
@app.route("/embed_image", methods=["POST"])
def embed_image():
    file = request.files["image"]
    img = Image.open(file.stream)

    embedding = model.encode(img, convert_to_tensor=True).tolist()

    return jsonify({"embedding": embedding})


# ============== 2. Поиск похожих фото ==============
@app.route("/search_similar", methods=["POST"])
def search_similar():
    db_vectors_list = request.json["db_vectors"]
    
    # Handle empty database
    if not db_vectors_list or len(db_vectors_list) == 0:
        return jsonify({"scores": [], "error": "No images in database to compare"})
    
    query_vec = torch.tensor(request.json["query_vector"]).unsqueeze(0)
    db_vectors = torch.tensor(db_vectors_list)

    scores = util.cos_sim(query_vec, db_vectors)[0]
    scores = scores.tolist()

    return jsonify({"scores": scores})


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5005)
