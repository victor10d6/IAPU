# Importar bibliotecas necessárias
import tensorflow as tf
import numpy as np
from PIL import Image
import requests
from io import BytesIO

# Carregar o modelo treinado
model = tf.keras.models.load_model('leaf_disease.h5')

# Função para pré-processamento da imagem
def preprocess_image(image_path):
    # Carregar a imagem e redimensioná-la para o tamanho esperado pelo modelo (224x224)
    img = Image.open(image_path)
    img = img.resize((224, 224))
    
    # Converter a imagem para um array numpy e normalizar os valores dos pixels
    img_array = np.array(img) / 255.0
    
    # Adicionar uma dimensão extra para corresponder à forma esperada pelo modelo (batch_size, height, width, channels)
    img_array = np.expand_dims(img_array, axis=0)
    
    return img_array

# Função para fazer uma predição usando o modelo
def predict(image_path):
    # Pré-processar a imagem
    input_data = preprocess_image(image_path)
    
    # Fazer a predição
    predictions = model.predict(input_data)
    
    # Decodificar as predições
    predicted_class = np.argmax(predictions)
    
    return predicted_class

# URL de uma imagem para teste (substitua pela sua própria URL ou caminho do arquivo)
image_url = "https://example.com/path/to/test_image.jpg"

# Baixar a imagem da URL
response = requests.get(image_url)
img = Image.open(BytesIO(response.content))

# Salvar a imagem localmente (opcional)
local_image_path = "test_image.jpg"
img.save(local_image_path)

# Fazer uma predição usando a imagem baixada
prediction = predict(local_image_path)

# Imprimir a classe predita
print("Classe predita:", prediction)
