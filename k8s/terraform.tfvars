# Cluster Name - Update this for your demo
cluster_name = "AWS_CLUSTER_THROUGH_WORKFLOW5"

# AWS Region
region = "eu-west-2"

# Kubernetes Version
kubernetes_version = "1.32"

# VPC Configuration
vpc_cidr           = "10.0.0.0/16"
availability_zones = ["eu-west-2a", "eu-west-2b"]

# Node Group Configuration
instance_types     = ["t3.medium"]
capacity_type      = "ON_DEMAND"
disk_size          = 20
desired_capacity   = 2
min_capacity       = 1
max_capacity       = 4

# Access Configuration
public_access_cidrs = ["0.0.0.0/0"]  # Restrict this to your IP for production

# ECR Configuration
ecr_repository_name = "demo-app-v5"

# Common Tags
common_tags = {
  Environment = "demo"
  Project     = "eks-demo"
  ManagedBy   = "terraform"
  Owner       = "DevOps-Team"
}