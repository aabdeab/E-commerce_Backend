# Architecture CQRS avec Kafka

## 📋 Vue d'ensemble

Ce projet implémente maintenant le pattern **CQRS (Command Query Responsibility Segregation)** en utilisant **Apache Kafka** comme bus d'événements pour séparer les commandes (write) des requêtes (read).

## 🏗️ Architecture CQRS

### **Pattern CQRS**
```
┌─────────────────────────────────────────────────────────────┐
│                     Client Applications                      │
└────────────────────────┬────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────┐
│              Product Composite Service                       │
│  ┌──────────────────┐          ┌──────────────────────┐    │
│  │   Commands       │          │      Queries         │    │
│  │  (CREATE/DELETE) │          │       (GET)          │    │
│  └────────┬─────────┘          └──────────┬───────────┘    │
└───────────┼────────────────────────────────┼────────────────┘
            │                                 │
            │ Kafka Events                    │ HTTP/WebClient
            ▼                                 ▼
┌───────────────────────┐       ┌────────────────────────┐
│   Kafka Topics        │       │   Microservices        │
│  - products           │       │  - product-service     │
│  - recommendations    │       │  - recommendation-srv  │
│  - reviews            │       │  - review-service      │
└───────────────────────┘       └────────────────────────┘
```

### **Flux des Commandes (Commands)**
1. **POST /product-composite** → Crée un nouveau produit avec ses recommendations et reviews
2. Le composite service publie des événements `CREATE` vers Kafka
3. Les microservices consomment ces événements et créent les entités dans leur base de données
4. **Asynchrone** : Retourne immédiatement sans attendre la persistance

### **Flux des Requêtes (Queries)**
1. **GET /product-composite/{id}** → Récupère un produit agrégé
2. Le composite service fait des appels HTTP synchrones (WebClient) vers les microservices
3. Agrège les résultats en un seul ProductAggregate
4. **Synchrone** : Attend les réponses de tous les services

## 🔧 Composants Implémentés

### 1. **Event Class** (`API/src/main/java/com/example/api/event/Event.java`)
```java
public class Event<K, T> {
    public enum Type { CREATE, DELETE }
    private Type eventType;
    private K key;              // Product ID
    private T data;             // Product/Recommendation/Review
    private ZonedDateTime eventCreatedAt;
}
```

### 2. **ProductCompositeIntegration** (Service Composite)
- **StreamBridge** : Publie les événements Kafka pour les commandes
- **WebClient** : Appels HTTP réactifs pour les queries
- **Scheduler** : Thread pool dédié pour la publication d'événements

**Topics Kafka :**
- `products-out-0` → Topic `products`
- `recommendations-out-0` → Topic `recommendations`
- `reviews-out-0` → Topic `reviews`

### 3. **Configuration Kafka** (`application.yml`)
```yaml
spring.cloud.stream:
  defaultBinder: kafka
  bindings:
    products-out-0:
      destination: products
      producer:
        required-groups: auditGroup
```

## 🚀 Démarrage

### **1. Démarrer Kafka**
```bash
docker-compose -f docker-compose-kafka.yml up -d
```

Cela démarre :
- **Zookeeper** (port 2181)
- **Kafka** (port 9092)
- **Kafka UI** (http://localhost:8090) - Interface de monitoring

### **2. Vérifier les Topics Kafka**
Accédez à http://localhost:8090 pour voir :
- Les topics créés automatiquement
- Les messages publiés
- Les groupes de consommateurs

### **3. Démarrer les Microservices**
```bash
# Product Service
cd microservices/product-service
gradlew bootRun

# Recommendation Service  
cd microservices/recommendation-service
gradlew bootRun

# Review Service
cd microservices/review-service
gradlew bootRun

# Product Composite Service
cd microservices/product-service-composite
gradlew bootRun
```

## 📡 API Endpoints

### **Commandes (Write)**
```bash
# Créer un produit avec recommendations et reviews
POST http://localhost:7000/product-composite
Content-Type: application/json

{
  "productId": 1,
  "name": "Product Name",
  "weight": 100,
  "recommendations": [
    {
      "recommendationId": 1,
      "author": "Author 1",
      "rate": 5,
      "content": "Great product!"
    }
  ],
  "reviews": [
    {
      "reviewId": 1,
      "author": "Reviewer 1",
      "subject": "Good",
      "content": "Very satisfied"
    }
  ]
}

# Supprimer un produit
DELETE http://localhost:7000/product-composite/1
```

### **Requêtes (Read)**
```bash
# Récupérer un produit agrégé
GET http://localhost:7000/product-composite/1
```

## 🔍 Monitoring Kafka

### **Via Kafka UI** (Recommandé)
1. Ouvrez http://localhost:8090
2. Naviguez vers "Topics"
3. Sélectionnez un topic (products, recommendations, reviews)
4. Visualisez les messages

### **Via CLI**
```bash
# Lister les topics
docker exec kafka kafka-topics --bootstrap-server localhost:29092 --list

# Consommer les messages d'un topic
docker exec kafka kafka-console-consumer \
  --bootstrap-server localhost:29092 \
  --topic products \
  --from-beginning
```

## 📦 Dépendances Ajoutées

### **product-service-composite/build.gradle**
```groovy
// Spring Cloud Stream with Kafka
implementation 'org.springframework.cloud:spring-cloud-starter-stream-kafka'

// Jackson for JSON serialization
implementation 'com.fasterxml.jackson.datatype:jackson-datatype-jsr310'

// Test
testImplementation 'org.springframework.cloud:spring-cloud-stream-test-binder'
```

## 🎯 Avantages du CQRS avec Kafka

✅ **Séparation des responsabilités** : Commands et Queries séparées  
✅ **Scalabilité** : Les writes et reads peuvent scaler indépendamment  
✅ **Performance** : Commands asynchrones, pas de blocage  
✅ **Résilience** : Kafka garantit la livraison des messages  
✅ **Audit** : Tous les événements sont persistés dans Kafka  
✅ **Event Sourcing** : Possibilité de rejouer les événements  

## 📊 Prochaines Étapes

Pour compléter l'architecture microservices, il reste à ajouter :

1. **Eureka Server** - Service Discovery
2. **API Gateway** - Point d'entrée unique
3. **OAuth2/OIDC** - Sécurité et authentification
4. **Message Processors** - Consommateurs Kafka dans les microservices

Voulez-vous que je continue avec l'une de ces implémentations ?

