# templates/mysql-configmap.yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: mysql-init-script
data:
  init.sql: |
    CREATE DATABASE IF NOT EXISTS tododb;
    USE tododb;