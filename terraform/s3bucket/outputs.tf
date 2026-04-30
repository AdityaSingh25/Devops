output "aws_s3_bucket_id" {
  value = aws_s3_bucket.s3bucket.id
}

output "aws_s3_bucket_name" {
  value = aws_s3_bucket.s3bucket.bucket
}

output "aws_s3_bucket_arn" {
  value = aws_s3_bucket.s3bucket.arn
}