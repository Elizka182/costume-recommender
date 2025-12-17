#!/bin/bash

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

print_step() {
    echo -e "${BLUE}===> $1${NC}"
}

print_success() {
    echo -e "${GREEN}✓ $1${NC}"
}

print_error() {
    echo -e "${RED}✗ $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠ $1${NC}"
}

echo "======================================"
echo "  Docker Cleanup Script"
echo "======================================"
echo ""

# Stop and remove containers
print_step "Stopping and removing containers..."

containers=(
    "costume-recommender"
    "costume-recommender-backend"
    "costume-recommender-frontend"
    "costume_recommender_db"
)

for container in "${containers[@]}"; do
    if docker ps -a --format '{{.Names}}' | grep -q "^${container}$"; then
        docker stop "$container" 2>/dev/null
        docker rm "$container" 2>/dev/null
        print_success "Removed container: $container"
    else
        print_warning "Container not found: $container"
    fi
done

# Remove images
print_step "Removing images..."

images=(
    "costume-recommender-backend"
    "costume-recommender-frontend"
)

for image in "${images[@]}"; do
    if docker images --format '{{.Repository}}' | grep -q "^${image}$"; then
        docker rmi "$image" 2>/dev/null || docker rmi -f "$image" 2>/dev/null
        print_success "Removed image: $image"
    else
        print_warning "Image not found: $image"
    fi
done

# Remove volume
print_step "Removing volume..."

volume="costume-recommender_mysql_data"

if docker volume ls --format '{{.Name}}' | grep -q "^${volume}$"; then
    docker volume rm "$volume" 2>/dev/null
    print_success "Removed volume: $volume"
else
    print_warning "Volume not found: $volume"
fi

# Optional: Remove dangling images
echo ""
read -p "Do you want to remove dangling images? (y/n): " -n 1 -r
echo ""
if [[ $REPLY =~ ^[Yy]$ ]]; then
    print_step "Removing dangling images..."
    docker image prune -f
    print_success "Dangling images removed"
fi

echo ""
echo "======================================"
echo "  Cleanup Complete"
echo "======================================"
echo ""
print_success "All specified Docker resources have been cleaned up!"