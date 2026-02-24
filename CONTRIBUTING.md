# Contributing to PoundSaver-AI

Thank you for your interest in contributing to PoundSaver-AI! This document provides guidelines and instructions for contributing.

## Code of Conduct

By participating in this project, you agree to maintain a respectful and inclusive environment for all contributors.

## Getting Started

1. Fork the repository
2. Clone your fork: `git clone https://github.com/YOUR_USERNAME/PoundSaver-AI.git`
3. Create a new branch: `git checkout -b feature/your-feature-name`
4. Make your changes
5. Run tests: `mvn test` (backend) and `npm test` (frontend)
6. Commit your changes: `git commit -m 'Add some feature'`
7. Push to your fork: `git push origin feature/your-feature-name`
8. Create a Pull Request

## Development Setup

Follow the instructions in the main README.md to set up your development environment.

## Coding Standards

### Backend (Java)
- Follow Java naming conventions
- Use meaningful variable and method names
- Add JavaDoc comments for public methods
- Keep methods focused and concise
- Write unit tests for new functionality
- Ensure code passes SonarQube quality gates

### Frontend (TypeScript/React)
- Use TypeScript for type safety
- Follow React best practices and hooks patterns
- Use functional components
- Keep components small and focused
- Write meaningful component and variable names
- Add comments for complex logic

## Testing

- Write unit tests for all new features
- Ensure existing tests pass before submitting PR
- Add integration tests where appropriate
- Update E2E tests for user-facing changes

## Pull Request Process

1. Update the README.md with details of changes if needed
2. Update documentation for API changes
3. Ensure all tests pass
4. Request review from maintainers
5. Address review feedback promptly

## Commit Message Guidelines

- Use present tense ("Add feature" not "Added feature")
- Use imperative mood ("Move cursor to..." not "Moves cursor to...")
- Limit first line to 72 characters
- Reference issues and pull requests when relevant

## Branch Naming

- Feature: `feature/description`
- Bug fix: `fix/description`
- Documentation: `docs/description`
- Performance: `perf/description`

## Questions?

Feel free to open an issue for any questions or concerns.
