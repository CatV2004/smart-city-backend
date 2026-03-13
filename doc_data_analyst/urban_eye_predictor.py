# import numpy as np
# from PIL import Image
# import tensorflow as tf

# class UrbanEyePredictor:
#     def __init__(self, model_path, class_names):
#         self.model = tf.keras.models.load_model(model_path)
#         self.class_names = class_names
#         self.input_size = (224, 224)
    
#     def predict(self, image_path, top_k=3):
#         # Load and preprocess image
#         img = Image.open(image_path).convert('RGB')
#         img = img.resize(self.input_size)
#         img_array = np.array(img) / 255.0
#         img_batch = np.expand_dims(img_array, axis=0)
        
#         # Predict
#         predictions = self.model.predict(img_batch, verbose=0)[0]
#         top_indices = np.argsort(predictions)[-top_k:][::-1]
        
#         results = []
#         for idx in top_indices:
#             results.append({
#                 'class': self.class_names[idx],
#                 'confidence': float(predictions[idx])
#             })
        
#         return results

# # Usage example:
# predictor = UrbanEyePredictor('best_model_finetuned.keras', class_names)
# results = predictor.predict('test_image.jpg')
# print(results)