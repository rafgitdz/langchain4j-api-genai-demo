# Acme Weather API: terms of use

Acme Weather is a fictitious company. This document exists only to demonstrate
retrieval-augmented generation.

## 1. Plans and quotas

The Free plan allows 1,000 requests per day and 10 requests per second. The Pro
plan costs 49 euros per month and allows 100,000 requests per day and 50
requests per second. The Enterprise plan is priced on quote and has no daily
quota.

Quotas reset every day at midnight UTC. A request over quota receives an HTTP
429 response with a Retry-After header.

## 2. API keys

Each account can create up to five API keys. A key must never be embedded in a
mobile application or a web page: call the API from your back end. A leaked key
must be revoked from the dashboard within 24 hours of its discovery.

## 3. Data and caching

Forecasts may be cached for up to 60 minutes. Historical data may be stored
without time limit, provided the source "Acme Weather" is displayed next to it.
Reselling raw data is forbidden on every plan.

## 4. Availability

Acme Weather targets 99.9 % monthly availability on the Pro and Enterprise
plans. Below that level, Pro customers receive a credit of 10 % of their monthly
fee, upon request within 30 days. The Free plan has no availability commitment.

## 5. Versioning and deprecation

Breaking changes are published in a new major version of the API. A deprecated
version stays available for 12 months after the announcement. Deprecation
notices are sent by e-mail and through the Sunset HTTP header.

## 6. Termination

A customer can close their account at any time from the dashboard. Acme Weather
may suspend an account that exceeds its quota repeatedly or resells data, after
a notice sent 15 days in advance, except in case of abuse affecting other
customers.
