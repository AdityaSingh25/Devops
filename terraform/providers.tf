provider "aws" {
  region = "us-east-1"
}

terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }

  backend "s3" {
    bucket = "adityastate0225"
    key    = "adityastate0225.tfstate"
    region = "us-east-1"
  }
}

