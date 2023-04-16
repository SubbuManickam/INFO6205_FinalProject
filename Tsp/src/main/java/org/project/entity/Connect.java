package org.project.entity;

public class Connect implements Comparable<Connect> {

        private Point from;
        private Point to;
        private double weight;

        public Point getFrom() {
                return from;
        }
        public void setFrom(Point from) {
                this.from = from;
        }
        public Point getTo() {
                return to;
        }
        public void setTo(Point to) {
                this.to = to;
        }
        public double getWeight() {
                return weight;
        }
        public void setWeight(double weight) {
                this.weight = weight;
        }

        public Connect(Point from, Point to, double weight) {
                this.from = from;
                this.to = to;
                this.weight = weight;
        }

        @Override
        public int compareTo(Connect newConnect) {
                return Double.compare(this.weight, newConnect.weight);
        }
}
