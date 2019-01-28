package ru.otus.hw1maven;

import org.apache.commons.collections4.list.TreeList;

import java.util.Random;

public class Main {

    public static class TestA
    {
        private int _num;
        private int _order;
        public TestA(int _order, int _num)
        {
            this._order = _order;
            this._num = _num;
        }

        public int get_num()
        {
            return this._num;
        }

        public int get_order()
        {
            return this._order;
        }


        @Override
        public String toString() {
            return "Pair(" + this.get_order() + ";" + this.get_num() + ")";
        }
    }

    public static void main(String[] args) {
        TreeList<TestA> treeList = new TreeList<>();

        Random rnd = new Random();

        for (int i = 0; i < 10; i++) {
            TestA item = new TestA(i,rnd.nextInt());

            treeList.add(i, item);
        }
        for (int i= 0 ; i< 10; i++)
        {
            System.out.println(Integer.toString(i) + " = " + treeList.get(i));
        }
    }
}
