# Configurando o Stack da Elastic

A forma mais simples que encontrei de rodar o stack foi através do Docker

1. Instale o Docker Engine e Docker Compose

      No Linux é facilmente encontrado nos repositórios, para Windows instale o [Docker Desktop](https://hub.docker.com/editions/community/docker-ce-desktop-windows/)
2. Teste a instalação fazendo ```docker run hello-world```      


3. Clone este [repositório](https://github.com/deviantony/docker-elk) com o ELK stack


4. Navegue até a pasta e rode ```docker-compose build```

5. Depois rode ```docker compose up```
---
## Se tudo deu certo o ELK estará rodando nas seguintes portas:

- Elasticsearch: 9200(HTTP) e 9300 (TCP Transport)
- Kibana: 5601
- Logstash: 5000

---
### Qualquer outra info está no repo do passo 3

![Containers](https://media.giphy.com/media/cUMNWzWZ5n75LvcCIe/giphy.gif)
